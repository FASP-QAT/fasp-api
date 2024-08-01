**API Documentation for Dashboard integration** 

**Table of contents**

[**Version information	1**](\#version-information)

[General information	2](\#general-information)

[1\. Authentication API	2](\#authentication-api)

[2\. Update expired password	3](\#update-expired-password)

[3\. Supply Plan API	3](\#supply-plan-api)

[4\. Data dictionaries	11](\#data-dictionaries)

[Supply plan	11](\#supply-plan)

[**5\. Errors	14**](\#errors)

[a. HttpStatusCode \- 401 (Unauthorized)	14](\#httpstatuscode---401-(unauthorized))

[b. HttpStatusCode \- 406 (Not Acceptable)	14](\#httpstatuscode---406-(not-acceptable))

[c. HttpStatusCode \- 404 (Not Found)	14](\#httpstatuscode---404-(not-found))

[d. HttpStatusCode \- 500 (Server error)	14](\#httpstatuscode---500-(server-error))

# **Version information** {#version-information}

| Version no | Created date | Created by | Change log |
| :---: | :---: | :---: | :---- |
| 1.1 | 29 Nov 2023 | [Dolly Chhabriya](mailto:dolly.c@altius.cc) | Created to share with FASP team and Dashboard vendor |
| 1.2 | 12 Jan 2024 | [Dolly Chhabriya](mailto:dolly.c@altius.cc) | Merged the Product catalog into Supply plan API so you do not need to call two distinct API’s |
| 1.3 | 31 Jul 2024 | [Rohit Vishwakarma](mailto:rohit.v@altius.cc) | Added a variable for start date so that we do not need to send the entire Supply Plan every time |
| 1.31 | 1 Aug 2024 | [Rohit Vishwakarma](mailto:rohit.v@altius.cc) | Updated the Error codes for different scenarios |

#  

# **General information** {#general-information}

To call any QAT API you will need a Profile that should be created by a QAT Administrator. If you have not received your credentials with which to call the API please reach out to [HSS-FASP-HQ@ghsc-psm.org](mailto:HSS-FASP-HQ@ghsc-psm.org)

You will need to reset the password at least once every 6 months.

To call a QAT API the user will need to first create a Token by calling an Authentication API 

1. # **Authentication API**  {#authentication-api}

   Method: POST   
   URL:[https://api.quantificationanalytics.org/authenticate](https://api.quantificationanalytics.org/%20authenticate)   
     
   Input :    
   {  
      "username":"\<%username%\>",  
      "password":"\<%password%\>"  
   }  
     
   Output :  
   {  
      "token": "absdfksjhtasckjnw;lfajre;lasc;lseth;lkwvajh;ljsae;lkrewglkjnhedsalkjvfhsd;lgjh"  
   }  
   

	Once you have the Token you need to use this token to call all other API’s. The Token once created will have a life of 6hrs. During those 6 hrs you can call any API with this Authorization Token to get the required results.

For all other API’s you will need to pass the following key value pair in the header of the API  
Key: Authorization  
Value: Bearer \<%Token%\>

1. # **Update expired password** {#update-expired-password}

   Method: POST  
   URL: [https://api.quantificationanalytics.org/api/updateExpiredPassword](https://api.quantificationanalytics.org/api/updateExpiredPassword)  
     
   Input:  
   {  
      "username":"\<%username%\>",  
      "oldPassword":"\<%old\_password%\>",  
      "newPassword":"\<%new\_password%\>"  
   }  
     
   Output:  
     
   

1. # **Supply Plan API** {#supply-plan-api}

   Call this API for the Supply plan of a particular Version of a Program. To get the Program Id of the Program please speak to an Administrator. If you know the VersionId that you want to get the data for you can specify the VersionId in the API call. If you do not know the VersionId you can set the VersionId \= \-1 in the API call. This will get you the data for the latest version of the Program.   
     
   Method: GET  
   URL: [https://api.quantificationanalytics.org/api/export/supplyPlan/programId/{programId}/versionId/{versionId}/startDate/{startDate}](https://api.quantificationanalytics.org/api/export/supplyPlan/programId/{programId}/versionId/{versionId}/startDate/{startDate})  
     
   Inputs:  
1. programId \- Program Id of the Program that you want to pull the Supply plan for. If you do not know the Program Id please get in touch with your Program manager  
1. versionId \- Every Supply plan is stored as a Version inside of QAT. If you need the latest version, pass “-1” as the versionId. If you want a specific version then pass that value.  
1. startDate \- The startDate is not a compulsory variable if you do not pass the startDate QAT will send you the entire Supply plan from the date that data is available. If you want data only after a startDate then pass that value. The startDate must be in the format YYYY-MM only. Due to the way QAT allows older Supply plan data to be changed the minimum cutOffDate allowed is 1year in the past. E.g. if today is July 20th 2024 you must have a cut off date of 2023-06 or earlier.  
     
   Output: 

| {    "programId": 2227,    "versionId": 1,    "program": {        "label": {            "label\_en": "Kenya PRH/CON",            "label\_sp": null,            "label\_fr": null,            "label\_pr": null        },        "healthAreaList": \[            {                "label": {                    "label\_en": "Population Reproductive Health",                    "label\_sp": "Planificación familiar",                    "label\_fr": "Planification familiale",                    "label\_pr": "Planeamento familiar"                }            },            {                "label": {                    "label\_en": "Condoms",                    "label\_sp": "Preservativos",                    "label\_fr": "Présservatifs",                    "label\_pr": "Preservativos"                }            }        \],        "organisation": {            "label": {                "label\_en": "Ministry of Health",                "label\_sp": null,                "label\_fr": null,                "label\_pr": null            }        },        "regionList": \[            {                "label": {                    "label\_en": "National",                    "label\_sp": "Nacional",                    "label\_fr": "National",                    "label\_pr": "Nacional"                }            }        \],        "currentVersionId": 14    },    "planningUnitList": \[        {            "planningUnit": {                "planningUnitId": 2478,                "label": {                    "label\_en": "Copper TCu380A Intrauterine Device, 1 Each",                    "label\_sp": null,                    "label\_fr": null,                    "label\_pr": null                },                "forecastingUnit": {                     "forecastingUnitId": 222,                    "label": {                        "label\_en": "Copper TCu380A Intrauterine Device",                        "label\_sp": null,                        "label\_fr": null,                        "label\_pr": null                    },                    "productCategory": {                        "label": {                            "label\_en": "Intrauterine Devices",                            "label\_sp": "",                            "label\_fr": "",                            "label\_pr": ""                        }                    }                },                "unit": {                    "label": {                        "label\_en": "Pieces",                        "label\_sp": "",                        "label\_fr": "",                        "label\_pr": ""                    }                },                "multiplier": 1.0            },            "supplyPlanData": \[                {                    "transDate": "2017-11-01",                    "regionCount": 1,                    "regionCountForStock": 0,                    "openingBalance": 0,                    "actualConsumptionQty": null,                    "forecastedConsumptionQty": null,                    "nationalAdjustment": 0,                    "manualPlannedShipmentQty": 0,                    "manualSubmittedShipmentQty": 0,                    "manualApprovedShipmentQty": 0,                    "manualOnholdShipmentQty": 0,                    "manualShippedShipmentQty": 0,                    "manualReceivedShipmentQty": 0,                    "erpPlannedShipmentQty": 0,                    "erpSubmittedShipmentQty": 0,                    "erpApprovedShipmentQty": 0,                    "erpOnholdShipmentQty": 0,                    "erpShippedShipmentQty": 0,                    "erpReceivedShipmentQty": 0,                    "expiredStock": 0,                    "closingBalance": 0,                    "mos": 0.0,                    "amc": 29562.0,                    "amcCount": 1,                    "unmetDemand": 0,                    "minStockMos": 18.0,                    "minStockQty": 532116.0,                    "maxStockMos": 24.0,                    "maxStockQty": 709488.0                },                {                    "transDate": "2017-12-01",                    "regionCount": 1,                    "regionCountForStock": 0,                    "openingBalance": 0,                    "actualConsumptionQty": null,                    "forecastedConsumptionQty": null,                    "nationalAdjustment": 0,                    "manualPlannedShipmentQty": 0,                    "manualSubmittedShipmentQty": 0,                    "manualApprovedShipmentQty": 0,                    "manualOnholdShipmentQty": 0,                    "manualShippedShipmentQty": 0,                    "manualReceivedShipmentQty": 0,                    "erpPlannedShipmentQty": 0,                    "erpSubmittedShipmentQty": 0,                    "erpApprovedShipmentQty": 0,                    "erpOnholdShipmentQty": 0,                    "erpShippedShipmentQty": 0,                    "erpReceivedShipmentQty": 0,                    "expiredStock": 0,                    "closingBalance": 0,                    "mos": 0.0,                    "amc": 24729.0,                    "amcCount": 2,                    "unmetDemand": 0,                    "minStockMos": 18.0,                    "minStockQty": 445122.0,                    "maxStockMos": 24.0,                    "maxStockQty": 593496.0                },                {                    "transDate": "2018-01-01",                    "regionCount": 1,                    "regionCountForStock": 1,                    "openingBalance": 0,                    "actualConsumptionQty": 29562,                    "forecastedConsumptionQty": 13975,                    "nationalAdjustment": 624960,                    "manualPlannedShipmentQty": 0,                    "manualSubmittedShipmentQty": 0,                    "manualApprovedShipmentQty": 0,                    "manualOnholdShipmentQty": 0,                    "manualShippedShipmentQty": 0,                    "manualReceivedShipmentQty": 0,                    "erpPlannedShipmentQty": 0,                    "erpSubmittedShipmentQty": 0,                    "erpApprovedShipmentQty": 0,                    "erpOnholdShipmentQty": 0,                    "erpShippedShipmentQty": 0,                    "erpReceivedShipmentQty": 0,                    "expiredStock": 0,                    "closingBalance": 595398,                    "mos": 25.27084686,                    "amc": 23560.6667,                    "amcCount": 3,                    "unmetDemand": 0,                    "minStockMos": 18.0,                    "minStockQty": 424092.0006,                    "maxStockMos": 24.0,                    "maxStockQty": 565456.0008                }            \]        },        {            "planningUnit": {                "planningUnitId": 2635,                "label": {                    "label\_en": "Medroxyprogesterone Acetate 150 mg/mL, IM Injection (1 mL Vial), 1 Vial",                    "label\_sp": null,                    "label\_fr": null,                    "label\_pr": null                },                "forecastingUnit": {                    "forecastingUnitId": 6071,                    "label": {                        "label\_en": "Medroxyprogesterone Acetate 150 mg/mL, IM Injection (1 mL Vial) Vial",                        "label\_sp": null,                        "label\_fr": null,                        "label\_pr": null                    },                    "productCategory": {                        "label": {                            "label\_en": "Injectable Contraceptives",                            "label\_sp": "",                            "label\_fr": "",                            "label\_pr": ""                        }                    }                },                "unit": {                    "label": {                        "label\_en": "Unit",                        "label\_sp": null,                        "label\_fr": null,                        "label\_pr": null                    }                },                "multiplier": 1.0            },            "supplyPlanData": \[                {                    "transDate": "2017-11-01",                    "regionCount": 1,                    "regionCountForStock": 0,                    "openingBalance": 0,                    "actualConsumptionQty": null,                    "forecastedConsumptionQty": null,                    "nationalAdjustment": 0,                    "manualPlannedShipmentQty": 0,                    "manualSubmittedShipmentQty": 0,                    "manualApprovedShipmentQty": 0,                    "manualOnholdShipmentQty": 0,                    "manualShippedShipmentQty": 0,                    "manualReceivedShipmentQty": 0,                    "erpPlannedShipmentQty": 0,                    "erpSubmittedShipmentQty": 0,                    "erpApprovedShipmentQty": 0,                    "erpOnholdShipmentQty": 0,                    "erpShippedShipmentQty": 0,                    "erpReceivedShipmentQty": 0,                    "expiredStock": 0,                    "closingBalance": 0,                    "mos": 0.0,                    "amc": 191645.0,                    "amcCount": 1,                    "unmetDemand": 0,                    "minStockMos": 18.0,                    "minStockQty": 3449610.0,                    "maxStockMos": 24.0,                    "maxStockQty": 4599480.0                },                {                    "transDate": "2017-12-01",                    "regionCount": 1,                    "regionCountForStock": 0,                    "openingBalance": 0,                    "actualConsumptionQty": null,                    "forecastedConsumptionQty": null,                    "nationalAdjustment": 0,                    "manualPlannedShipmentQty": 0,                    "manualSubmittedShipmentQty": 0,                    "manualApprovedShipmentQty": 0,                    "manualOnholdShipmentQty": 0,                    "manualShippedShipmentQty": 0,                    "manualReceivedShipmentQty": 0,                    "erpPlannedShipmentQty": 0,                    "erpSubmittedShipmentQty": 0,                    "erpApprovedShipmentQty": 0,                    "erpOnholdShipmentQty": 0,                    "erpShippedShipmentQty": 0,                    "erpReceivedShipmentQty": 0,                    "expiredStock": 0,                    "closingBalance": 0,                    "mos": 0.0,                    "amc": 199180.0,                    "amcCount": 2,                    "unmetDemand": 0,                    "minStockMos": 18.0,                    "minStockQty": 3585240.0,                    "maxStockMos": 24.0,                    "maxStockQty": 4780320.0                },                {                    "transDate": "2018-01-01",                    "regionCount": 1,                    "regionCountForStock": 1,                    "openingBalance": 0,                    "actualConsumptionQty": 191645,                    "forecastedConsumptionQty": 471958,                    "nationalAdjustment": 3484916,                    "manualPlannedShipmentQty": 0,                    "manualSubmittedShipmentQty": 0,                    "manualApprovedShipmentQty": 0,                    "manualOnholdShipmentQty": 0,                    "manualShippedShipmentQty": 0,                    "manualReceivedShipmentQty": 3772200,                    "erpPlannedShipmentQty": 0,                    "erpSubmittedShipmentQty": 0,                    "erpApprovedShipmentQty": 0,                    "erpOnholdShipmentQty": 0,                    "erpShippedShipmentQty": 0,                    "erpReceivedShipmentQty": 0,                    "expiredStock": 0,                    "closingBalance": 7065471,                    "mos": 36.38032282,                    "amc": 194211.3333,                    "amcCount": 3,                    "unmetDemand": 0,                    "minStockMos": 18.0,                    "minStockQty": 3495803.9994,                    "maxStockMos": 24.0,                    "maxStockQty": 4661071.9992                }            \]        }    \]} |
| :---- |

   

   

1. # **Data dictionaries** {#data-dictionaries}

## Supply plan {#supply-plan}

| S. no | Field name | Description |
| :---- | :---- | :---- |
| 01 | programId | Program Id |
| 02 | versionId | Version id of the Supply Plan |
| 03 | program | Program object |
| 03.01 | ....label | Program label object |
| 03.01.01 | ........label\_en | English name |
| 03.01.02 | ........label\_sp | Spanish name |
| 03.01.03 | ........label\_fr | French name |
| 03.01.04 | ........label\_pr | Portuguese name |
| 03.02 | ....healthAreaList | List of Health Areas objects that this Program is for |
| 03.02.01 | ........label | Health Area label object |
| 03.02.01.01 | ............label\_en | English name |
| 03.02.01.02 | ............label\_sp | Spanish name |
| 03.02.01.03 | ............label\_fr | French name |
| 03.02.01.04 | ............label\_pr | Portuguese name |
| 03.03 | ....organisation | Organisation that this Program is for |
| 03.03.01 | ........label | Organisation label object |
| 03.03.01.01 | ............label\_en | English name |
| 03.03.01.02 | ............label\_sp | Spanish name |
| 03.03.01.03 | ............label\_fr | French name |
| 03.03.01.04 | ............label\_pr | Portuguese name |
| 03.04 | ....regionList | List of Regions part of this Program |
| 03.04.01 | ........label | Region label object |
| 03.04.01.01 | ............label\_en | English name |
| 03.04.01.02 | ............label\_sp | Spanish name |
| 03.04.01.03 | ............label\_fr | French name |
| 03.04.01.04 | ............label\_pr | Portuguese name |
| 03.05 | ....currentVersionId | Latest version id for this Program |
| 04 | planningUnitList | List of Planning Units part of the Supply Plan |
| 04.01 | ....planningUnit | Planning Unit object |
| 04.01.01 | ........planningUnitId | Planning Unit Id |
| 04.01.02 | ........label | Planning Unit label object |
| 04.01.02.01 | ............label\_en | English name |
| 04.01.02.02 | ............label\_sp | Spanish name |
| 04.01.02.03 | ............label\_fr | French name |
| 04.01.02.04 | ............label\_pr | Portuguese name |
| 04.01.03 | ........forecastingUnit | Forecasting Unit that the Planning Unit belongs to |
| 04.01.03.01 | ............forecastingUnitId | Forecasting Unit Id |
| 04.01.03.02 | ............label | Forecasting Unit label object |
| 04.01.03.02 | ................label\_en | English name |
| 04.01.03.02 | ................label\_sp | Spanish name |
| 04.01.03.02 | ................label\_fr | French name |
| 04.01.03.02 | ................label\_pr | Portuguese name |
| 04.01.03.03 | ............productCategory | Product category object that this Forecasting Unit belongs to |
| 04.01.03.03 | ................label | Product category label object |
| 04.01.03.03 | ....................label\_en | English name |
| 04.01.03.03 | ....................label\_sp | Spanish name |
| 04.01.03.03 | ....................label\_fr | French name |
| 04.01.03.03 | ....................label\_pr | Portuguese name |
| 04.01.04 | ........unit | Unit object that this Planning Unit belongs to |
| 04.01.04.01 | ............id | Unit Id |
| 04.01.04.02 | ............label | Unit label object |
| 04.01.04.02 | ................label\_en | English name |
| 04.01.04.02 | ................label\_sp | Spanish name |
| 04.01.04.02 | ................label\_fr | French name |
| 04.01.04.02 | ................label\_pr | Portuguese name |
| 04.01.05 | ........multiplier | Multiply this into Planning Unit quantity to get the Forecasting Unit quantity |
| 05.02 | ....supplyPlanData | List of supply plan data for each month (All quantities are in terms of PU) |
| 05.01 | ....yyyy-mm-dd | Month that this record is for |
| 05.02.01 | ........regionCount | No of regions in this months data |
| 05.02.02 | ........regionCountForStock | No of regions that provided Consumption count for this month |
| 05.02.03 | ........openingBalance | Opening Balance |
| 05.02.04 | ........actualConsumptionQty | Actual Consumption, if not provided it will be null |
| 05.02.05 | ........forecastedConsumptionQty | Forecasted Consumption, if not provided it will be null |
| 05.02.06 | ........nationalAdjustment | Adjustment either Manual or automatically calculated |
| 05.02.07 | ........manualPlannedShipmentQty | Manual Shipment expected to arrive this month, current status is Planned. |
| 05.02.08 | ........manualSubmittedShipmentQty | Manual Shipment expected to arrive this month, current status is Submitted. |
| 05.02.09 | ........manualApprovedShipmentQty | Manual Shipment expected to arrive this month, current status is Approves. |
| 05.02.10 | ........manualOnholdShipmentQty | Manual Shipment expected to arrive this month, current status is OnHold. |
| 05.02.11 | ........manualShippedShipmentQty | Manual Shipment expected to arrive this month, current status is Shipped. |
| 05.02.12 | ........manualReceivedShipmentQty | Manual Shipment expected to arrive this month, current status is Received. |
| 05.02.13 | ........erpPlannedShipmentQty | ERP linked Shipment expected to arrive this month, current status is Planned. |
| 05.02.14 | ........erpSubmittedShipmentQty | ERP linked Shipment expected to arrive this month, current status is Submitted. |
| 05.02.15 | ........erpApprovedShipmentQty | ERP linked Shipment expected to arrive this month, current status is Approves. |
| 05.02.16 | ........erpOnholdShipmentQty | ERP linked Shipment expected to arrive this month, current status is OnHold. |
| 05.02.17 | ........erpShippedShipmentQty | ERP linked Shipment expected to arrive this month, current status is Shipped. |
| 05.02.18 | ........erpReceivedShipmentQty | ERP linked Shipment expected to arrive this month, current status is Received. |
| 05.02.19 | ........expiredStock | Qty of PU's expiring this month |
| 05.02.20 | ........closingBalance | Closing Balance |
| 05.02.21 | ........mos | Calculated Months of Stock |
| 05.02.22 | ........amc | Calculated Average Monthly Consumption |
| 05.02.23 | ........amcCount | No of months used to calculate AMC |
| 05.02.24 | ........unmetDemand | Calculated Unmet demand |
| 05.02.25 | ........minStockMos | Minimum Stock in terms of Months of Stock |
| 05.02.26 | ........minStockQty | Minimum Stock in terms of Qty |
| 05.02.27 | ........maxStockMos | Maximum Stock in terms of Months of Stock |
| 05.02.28 | ........maxStockQty | Maximum Stock in terms of Qty |

1. # **Errors** {#errors}

   1. ## HttpStatusCode \- 401 (Unauthorized) {#httpstatuscode---401-(unauthorized)}

      You can get this error if you have used an incorrect or invalid Token to call the API or if you have not passed the Token

   1. ## HttpStatusCode \- 406 (Not Acceptable) {#httpstatuscode---406-(not-acceptable)}

      You can get this error if the startDate provided is not of the correct format. The acceptable format is “YYYY-MM”. Or if the value of the startDate is less than 1 year since today.

   1. ## HttpStatusCode \- 404 (Not Found) {#httpstatuscode---404-(not-found)}

      You can get this error if the ProgramId that you have provided in the input does not have any data or if you do not have the rights to access this Program Id  
      

   1. ## HttpStatusCode \- 500 (Server error) {#httpstatuscode---500-(server-error)}

      An unknown error has occurred. Please provide us with the following information to help debug   
* Exact URL that you had called  
* The timestamp that the API was called  
* Error code and message that you received