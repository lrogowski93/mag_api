# Mag_API

## Description
Simple REST Api for placing orders in Wapro Mag without knowledge of SQL procedures or database schema.

All you have to do is prepare JSON request with list of ordered products. Required procedures and functions are called in the background.

## Features

- placing order with specified products (quantity, price, notes)
- canceling not confirmed orders
- checking order status
- authentication using JWT

## Tech Stack

* Java 17
* Spring Boot 3.0.0
* Oracle JDBC driver
* MS SQL JDBC driver
* Lombok

## Installation

1. Clone repository

```bash
git clone https://github.com/lrogowski93/mag_api
```
2. Change name of [EXAMPLE_application.properties](/src/main/resources/EXAMPLE_application.properties) file to application.properties.
3. Set up config in application.properties file (access to Mag database, authentication database, Mag config).
* Wapro Mag configuration is based on official [SQL documentation](https://wapro24bis.assecobs.pl/plikownia/WAPRO%20Mag/Dokumentacja%20techniczna/WAPRO%20Mag%20Dokumentacja%20procedur%20SQL.pdf):
```properties
mag.cfg.companyid= ID_FIRMY from table FIRMA (company where the order is placed)
mag.cfg.warehouseid= ID_MAGAZYNU from table MAGAZYN (warehouse from which the order is processed)
mag.cfg.userid= ID_UZYTKOWNIKA from table UZYTKOWNIK (user adding an order)
mag.cfg.employeeid= ID_PRACOWNIKA from table PRACOWNIK (employee responsible for an order)
mag.cfg.orderdoctypeid= ID_TYPU from table TYP_DOKUMENTU_MAGAZYNOWEGO (order from the customer document type)
```
* non-standard settings (e.g. order payment methods) can be done in each [SQL procedure class](/src/main/java/mag/model/procedure).
4. Create authentication database schema.
* manually: use SQL for creating table provided in [01-create-users.sql](/src/main/resources/database/2022-05-25/01-create-users.sql)
* automatically **(for Mag database safety reasons, I strongly recommend not to create schema automatically)**: change value of `spring.jpa.hibernate.ddl-auto` key in application.properties, after first run and creating schema, change value back to `none`
```properties
spring.jpa.hibernate.ddl-auto=create
```
5. Add users to database:
   `mag_id` = ID_KONTRAHENTA from table KONTRAHENT (customer placing order).
```sqk
INSERT INTO users(mag_id, username, password, enabled) VALUES (1234,'user','{bcrypt}PUT_HERE_PASSWORD_HASH',1)
```

6. Provide public/private RSA [key pair](/src/main/resources/certs). You can use [key generator](https://cryptotools.net/rsagen)  or generate keys manually with OpenSSL.

7. Run app by using following command:
```bash
gradlew bootRun
```

## Usage

### Sign in (basic auth)

```http
  POST /signin
```
#### Header
| Parameter       | Description                                            |
|-----------------|--------------------------------------------------------|
| `Authorization` | **Required** `Basic login:password` _(base64 encoded)_ |

#### Sign in response

| Parameter      | Type   | Description           |
|----------------|--------|-----------------------|
| `access_token` | string | JWT                   |
| `token_type`   | string | Token type            |
| `expires_in`   | long   | Token expiration time |

### All endpoints other than _/signin_ must be secured with JWT
#### Header

| Parameter       | Description                   |
|-----------------|-------------------------------|
| `Authorization` | **Required** `Bearer` _token_ |


### Add order

```http
  POST /orders
```
* sample request
```json
  {
   "orderItems" : [ {
      "index": "99A28A",
      "quantity" : 100,
      "netPrice" : 30.1,
      "vat" : "23",
      "description": "sample description"
   }, {
      "index": "ZW8D",
      "quantity": 105,
      "netPrice" : 0.5,
      "vat" : "23"
   }, {
         "index": "A1",
         "quantity": 105,
         "netPrice" : 10.21,
         "vat" : "0"
      }],
   "notes" : "some note"
}
```

| Parameter    | Type             | Description                             |
|--------------|------------------|-----------------------------------------|
| `orderItems` | Array of objects | **Required** Array of orderItem objects |
| `notes`      | String           | Order notes                             |

#### orderItem
| Parameter     | Type    | Description           |
|---------------|---------|-----------------------|
| `index`       | string  | **Required** Index    |
| `quantity`    | integer | **Required** Quantity |
| `netPrice`    | decimal | Net price             |
| `vat`         | string  | **Required** VAT rate |
| `description` | string  | Notes/description     |

#### Add order response

| Parameter    | Type             | Description                      |
|--------------|------------------|----------------------------------|
| `status`     | String           | OK / Errors occurred             |
| `orderId`    | long             | ID of created order              |
| `orderItems` | Array of objects | Array of added orderItem objects |


### Check order status

```http
  GET /orders/{orderId}
```

#### Check order status response

* Order confirmed

| Parameter    | Type             | Description                          |
|--------------|------------------|--------------------------------------|
| `orderItems` | Array of objects | Array of confirmed orderItem objects |

* Order not confirmed

```http
  HTTP STATUS: 404
```

### Cancel order

```http
  DELETE /orders/{orderId}
```

#### Cancel order response

* Order cancelled
```http
  HTTP STATUS: 204
```

* Order not cancelled (e.g. already cancelled or confirmed)
```http
  HTTP STATUS: 404
```

## Appendix
SQL procedures and their parameters names may be confusing and unclear, this is due to the way they are programmed by the manufacturer of the Wapro Mag software. The manufacturer uses the Polish language in its procedures. I tried to translate everything as accurately as possible. Some procedure parameters are obsolete but has to be used. If you need more specific configuration you can look at official [SQL documentation](https://wapro24bis.assecobs.pl/plikownia/WAPRO%20Mag/Dokumentacja%20techniczna/WAPRO%20Mag%20Dokumentacja%20procedur%20SQL.pdf) (it can contain typos and errors) and modify [SQL procedure classes](/src/main/java/mag/model/procedure) as you wish.

## License
This project is licensed under the MIT license. Please see the [LICENSE](/LICENSE) file distributed with this source code for further information.

Be aware that Wapro Mag software is not licensed under the MIT. Check out official website to read about terms, pricing and license of Wapro Mag before using their software:
* [wapro.pl](https://wapro.pl/)