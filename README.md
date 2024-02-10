## Redis Client-Server Basic

* `redis-server`: test redis server
* `redis-cli -h localhost -p 6379`: test redis cli
* `redis-server config/redis.conf`: menjalankan redis server menggunakan file config
* `select <index>`: memilih database berdasarkan index di redis client
* `set <key> "value"`: menambahkan data
* `get <key>`: mengambil data
* `append <key> "value"`: menambahkan data dari yang sudah ada
* `exists <key>`: melihat status data
* `del <key>`: menghapus data
* `keys *`: mengambil semua keys yg ada di db
* `mset <key> "value" <key> "value"`: menambahkan data lebih dari satu
* `mget <key> <key>`: mengambil data lebih dari satu