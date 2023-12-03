#!/usr/bin/env bash
set -e

psql "user=postgres hostaddr=127.0.0.1 port=5431 password=postgres" < cars/create.sql
psql "user=postgres hostaddr=127.0.0.1 port=5431 password=postgres" < payment/create.sql
psql "user=postgres hostaddr=127.0.0.1 port=5431 password=postgres" < rental/create.sql
psql "user=postgres hostaddr=127.0.0.1 port=5431 password=postgres" < cars/insert.sql