## Redis Client-Server Basic

* `redis-server`: test redis server
* `redis-cli -h localhost -p 6379`: test redis cli
* `redis-server config/redis.conf`: menjalankan redis server menggunakan file config
* `select <index>`: memilih database berdasarkan index di redis client
* `set <nama key> "value"`: menambahkan data
* `get <nama key>`: mengambil data
* `append <nama key> "value"`: menambahkan data dari yang sudah ada
* `exists <nama key>`: melihat status data
* `del <nama key>`: menghapus data