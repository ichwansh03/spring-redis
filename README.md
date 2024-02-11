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
* `flushdb`: menghapus key pada db saat ini
* `flushall`: menghapus key pada semua db
* `setex <key> <seconds> "value"`: mengatur waktu expire key
* `redis-cli -h localhost -p 6379 --pipe < nama-file.txt`: input file dalam jumlah besar
* `multi`: memulai transactional pada db
* `exec`: eksekusi keys dan lakukan commit
* `discard`: rollback keys
* `client list`: untuk mendapatkan list client
* `redis-clit -h <ip-address> -p 6379`: mengubah localhost menjadi ip local, ubah dulu ip loopback pada file config
* `auth <username> <password>`: autentikasi menggunakan username dan password yg sudah didaftarkan pada file config 
```markdown
user default on +@connection
user ichwan on +@all ~* >secret123
```

[reference](https://docs.google.com/presentation/d/1kDwmRom2R7JioqkUh6mT1ohjy0t1kRQQHR1VwWgT-b0/edit?usp=sharing)