(ns jenkins-builds-to-csv.core
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.zip :as zip]
            [clojure.data.xml :as xml]
            [clojure.data.csv :as csv]
            [clojure.data.zip.xml :as zx]))

(def columns [:name :startTime :result :duration])

(defn jobs [path]
  (filter #(.isDirectory %) (.listFiles (io/file path))))

(defn build-xmls [job-dir]
  (pmap #(slurp %) (filter #(= "build.xml" (.getName %)) (file-seq job-dir))))

(defn csv-data [name xml-str]
  (let [build (xml/parse-str xml-str)
        build-zip (zip/xml-zip build)
        columns (remove #(= :name %) columns)
        data (into {} (map #(hash-map % (zx/xml1-> build-zip % zx/text)) columns))]
    (conj {:name name} data)))

(defn write-csv [path row-data]
  (let [headers (map name columns)
        rows (mapv #(mapv % columns) row-data)]
    (with-open [file (io/writer path)]
      (csv/write-csv file (cons headers rows)))))

(defn data-for-job [job-dir]
  (let [name (.getName job-dir)
        builds (build-xmls job-dir)]
    (println name)
    (pmap #(csv-data name %) builds)))

(defn csv-for-job [job-dir]
  (let [name (.getName job-dir)
        path (str (.getCanonicalPath job-dir) "/builds.csv")
        raw-data (data-for-job job-dir)]
    (write-csv path raw-data)))

(defn csv-for-all [jobs-dir]
  (let [path (str (.getCanonicalPath (io/file jobs-dir)) "/builds.csv")
        raw-data (flatten (pmap #(data-for-job %) (jobs jobs-dir)))]
    (write-csv path raw-data)))

(defn -main
  [& args]
  (let [path (first args)]
    (csv-for-all path)
    (println "done")
    (System/exit 0)))
