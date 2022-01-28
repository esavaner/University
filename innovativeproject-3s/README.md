# innovativeproject-3s
Security Scan Scheduler

Things to do before running trivy scans

1. Create database: db3s
2. in databse do: source <PATH_TO_QUARTZ_TABLES.sql>
3. mvn spring-boot:run

4. Test scan like that:

curl -i -H "Content-Type: application/json" -X POST \
-d '{"email":"nokiascaner3s@gmail.com","subject":"Test","body":"Dummy message","dateTime":"2019-12-17T15:10:01","timeZone":"Europe/Warsaw"}' \
http://localhost:8080/scheduleScan
