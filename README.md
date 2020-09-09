# feature-toggle
Simple back-end application to support release of any feature safely(Canary release) and to target users dynamically.

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Demo](#demo)
* [Todo](#todo)

## General info
Generally feature toggle applications allows release of any new feature to specific users or any groups of users who comes under certain conditions. These conditions attribute value are generally configured in dashboard and the client will request with the user's attribute value to check whether user follows the conditions.

In addition to this, this project focusses on dynamically varying the attributes(either addition of new attribute or removal of existing ones in the conditions) so that client does not need the code change to add user attribute value. To acheive this, it's necessary to identify the entities(can be userId or combination of userId and orderId etc..) to whom feature is to be released. So only these entities value needs to be passed from client side and the rest of the attribute values can be computed in this application by executing a query using the entities value as input.

In monolithic architecture, this project supports well in terms of single database connection and in case of microservice architecture, the workaround needs to be made to support query execution in the client service where request made.

## Technologies

* Java 1.8
* Spring Boot 2.3.3
* Apache Maven 3.6.1
* MySQL 8.0.16

## Setup

1) Import project and add it as Maven project.

2) Create database named 'feature_toggle' and tables will be created once project runs.

   Secondary database can be any MySQL database where client service is connected.

   Update in application.yml with appropriate credentials. Here is the sample:

   ```
   datasource:
    jdbc-url: jdbc:mysql://localhost:3306/feature_toggle
    username: root
    password: root@1234
    driver:
      class: com.mysql.jdbc.Driver

   secondary-datasource:
    jdbc-url: jdbc:mysql://localhost:3306/movie1
    username: root
    password: root@1234
    driver:
      class: com.mysql.jdbc.Driver

   ```
## Demo

* Add a sample entry in environment table: (From dashboard)
  ```
  curl --location --request POST 'http://localhost:9080/feature-toggle/environment/add?name=qa&key=qa'
  ```
* Creating a new feature: (From dashboard)

  Discount percentage for users

  ```
  curl --location --request POST 'http://localhost:9080/feature-toggle/feature/create' \
  --header 'Content-Type: application/json' \
  --data-raw '{
	"featureName" : "Discount Offer",
	"featureKey" : "discount_offer",
	"env" : "qa",
	"description" : "Discount Offer based on the booking amount within the last one month",
	"variationType" : "NUMBER",
	"variations" : [
		{
			"variationName" : "20 percent",
			"variationValue" : 20,
			"targetOffVariation" : false,
			"targetOnDefaultVariation" : false
		},
		{
			"variationName" : "10 percent",
			"variationValue" : 10,
			"targetOffVariation" : false,
			"targetOnDefaultVariation" : false
		},
		{
			"variationName" : "No discount",
			"variationValue" : 0,
			"targetOffVariation" : true,
			"targetOnDefaultVariation" : true
		}
	],
    "featureEntityDependenceRequestModels": [
        {
            "entityKey" : "userId",
            "description" : "User Id"
        }
    ],
    "queryString" : " SELECT SUM(amount) as bookedAmount FROM Booking b where DATE(booking_date) between (CURDATE() - INTERVAL 1 MONTH ) and CURDATE() and    user_id = (:userId) "
  }'

  ```
* Enable the feature: (From dashboard)
  ```
  curl --location --request POST 'http://localhost:9080/feature-toggle/feature/enable/4?enable=true'
  ```
* Updating the Conditions: (From dashboard)

  Conditions based on priority :
    1) If userId is 1 and bookedAmount >= 1500, give 20 percent
    2) For other users, if bookedAmount >= 1000, give 10 percent
    3) Else no discount. (Added as targetOnDefaultVariation and targetOffVariation)

  ```
  curl --location --request POST 'http://localhost:9080/feature-toggle/feature/saveEvaluationRules/4' \
  --header 'Content-Type: application/json' \
  --data-raw '[
    {
		"serve" : 20,
		"rules" : [
            {
				"ruleEvaluationStrategy" :"CUSTOM_ATTRIBUTE",
				"data" : {
					"key" : "userId",
					"customAttributesOperator" : "EQUALS",
					"values" : [1]
				}
			},
			{
				"ruleEvaluationStrategy" :"CUSTOM_ATTRIBUTE",
				"data" : {
					"key" : "bookedAmount",
					"customAttributesOperator" : "GREATER_THAN_OR_EQUAL_TO",
					"values" : 1500
				}
			}
		]
	},
	{
		"serve" : 10,
		"rules" : [
			{
				"ruleEvaluationStrategy" :"CUSTOM_ATTRIBUTE",
				"data" : {
					"key" : "bookedAmount",
					"customAttributesOperator" : "GREATER_THAN_OR_EQUAL_TO",
					"values" : 1000
				}
			}
		]
	}
  ]'
  ```
* ### Getting the status of the feature: (From Client Service)

  Pass featureEntityDepedences(here userId) and any other additional attributes to the request body.

  Sample Input from db :

    UserId - 1 BookedAmount - 1500

    UserId - 2 BookedAmount - 2000

    UserId - 3 BookedAmount - 800

  ```
  Request:

  curl --location --request GET 'http://localhost:9080/feature-toggle/feature/getStatus/qa/discount_offer' \
  --header 'Content-Type: application/json' \
  --data-raw '{
    "userId" : 1
  }'

  Response:

  {
    "status": "success",
    "data": 20
  }
  ```

  ```
  Request:

  curl --location --request GET 'http://localhost:9080/feature-toggle/feature/getStatus/qa/discount_offer' \
  --header 'Content-Type: application/json' \
  --data-raw '{
    "userId" : 2
  }'

  Response :

  {
    "status": "success",
    "data": 10
  }
  ```

  ```
  Request:

  curl --location --request GET 'http://localhost:9080/feature-toggle/feature/getStatus/qa/discount_offer' \
  --header 'Content-Type: application/json' \
  --data-raw '{
    "userId" : 3
  }'

  Response:

  {
    "status": "success",
    "data": 0
  }
  ```

## Todo

* Supporting in microservice architecture

    To execute query in the microservice by registering url in the environment table and get back response to perform evaluation.

* Addition of more CustomAttributeOperator

    Currently supporting Equals, Regex and GreaterThanOrEqualTo

