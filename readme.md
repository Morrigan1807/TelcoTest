1. Build project with maven goal: `mvn clean install`
2. Fill property file as:
   ```
   sftp_host=127.0.0.1
   sftp_port=22
   sftp_user=user
   sftp_password=password
   sftp_remote_dir=/path/to/remotedir
   local_dir=/path/to/localdir
   sql_user=user_name
   sql_password=password
   sql_database=database_name
   ```
3. Run project in terminal with command: `java src/main/java/Main.java <path>`.
Replace `<path>` with the path to property file.