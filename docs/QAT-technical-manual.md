# **Technical Documentation/System Administrator Guide**

**Document details:**

| Version (date) | Author/Editor | Notes & Major Changes |
| :---- | :---- | :---- |
| 1 (Mar 15, 2021\) | GHSC-PSM FASP, Altius & FHI 360 | First draft |
| 2 (Sept 14, 2022\) | Kyle Duarte | Updated to include Forecasting Module |
| 3 (Aug 01, 2024\) | Akil Mahimwala | Updated to include reference to Integration documentation. |

**Acronyms & Definitions**

| ARTMIS | Automated Requisition Tracking Management Information System |
| :---- | :---- |
| CDN | Content Delivery Network |
| ERP | Enterprise Resource planning |
| FASP | Forecasting and Supply Planning |
| GFPVAN | Global Family Planning Visibility & Analytics Network |
| GHSC-PSM | Global Health Supply Chain \- Procurement and Supply Management program |
| IAM | Identity and Access Management |
| JWT | JSON Web Tokens |
| QAT | Quantification Analytics Tool |
| RDS | Relational Database Service |
| VPC | Virtual Private Cloud |
| USAID | United States Agency for International Development |

Table of Contents  
[1 Purpose of Document](\#purpose-of-document)  
[2 Introduction to QAT](\#introduction-to-qat)  
[3 High-level architecture](\#high-level-architecture)  
[3.1 Front End \- React based PWA](\#front-end---react-based-pwa)  
[3.2 Back End \- Java Spring Boot Application](\#back-end---java-spring-boot-application)  
[3.3 Front End & Back End Sync Process](\#front-end--back-end-sync-process)  
[4 QAT-ERP Integrations](\#qat-erp-integrations)  
[4.1 QAT sharing Supply Plan with external systems](\#qat-sharing-supply-plan-with-external-systems)  
[4.2 QAT Catalog Updates](\#qat-catalog-updates)  
[4.3 ERP Shipment linking (coming soon)](\#erp-shipment-linking-coming-soon)  
[5 Setting up & Managing QAT Server Side Application](\#setting-up--managing-qat-server-side-application)  
[5.1 Source code](\#source-code)  
[5.2 Database Design](\#database-design)  
[5.3 Server Design](\#server-design)  
[5.4 Operating system requirements](\#operating-system-requirements)  
[5.5 Other Software](\#other-software)  
[5.6 Setup process](\#setup-process)  
[5.7 List of API's](\#list-of-apis)  
[5.8 How to call an API](\#how-to-call-an-api)  
[5.9 React Specifications](\#react-specifications)  
[5.10 React App Installation steps](\#react-app-installation-steps)  
[5.11 Running QAT Application](\#running-qat-application)  
[5.12 Running R based reports](\#running-r-based-reports)  
[6 Integration with Country Dashboard](\#integration-with-country-dashboard)  
[Annex 1: Business & Technical Requirements](\#annex-1-business--technical-requirements)  
[Annex 2: Entity-Relationship Diagrams](\#annex-2-entity-relationship-diagrams)  
[Annex 3: Data Dictionary](\#annex-3-data-dictionary)

## Purpose of Document

This document seeks to provide the technical overview details of the Quantification Analytics Tool (QAT) Server and provide a guide to any IT professionals wishing to further understand the back end of QAT, including the software tools used, high-level design architecture, data interfaces/integration, and server-side details on configuration and management. For example, developers who are expected to take over and do code maintenance on the system over its life span. This does not include the application and realm admin functions that are available on the front end of the software, as those are covered in the QAT user guide.

## Introduction to QAT

The Quantification Analytics Tool (QAT) is a modernized solution for country-led forecasting and supply planning. QAT leverages new technologies to enhance and modernize the functionality offered by the incumbent PipeLine (supply planning) and Quantimed (forecasting) tools by providing advanced visualizations, master data management, and updated planning logic on a cloud-based solution with offline functionality.

QAT is being built in two modules \- the supply planning module (launched December 2020\) and the forecasting module (launched June 2022). For more on the Functional Requirements used to build QAT, please see Annex 1: Business & Technical Requirements.

## High-level architecture

QAT is built on Java spring boot backend and React front end. The high-level architecture is below.

### Front End \- React based PWA

The React based application is hosted on a NodeJS server. It is built as a PWA and therefore works even if the User is offline. To initialize the application the User will have to login to the application once while he is online. The Username and Password that the user has entered is sent to the backend and once authorized a Token is returned. The Local application then does all subsequent calls to the API using the Token to identify and authenticate the request.

At the first login the application Syncs all the Master data with the Server side pulling in any updates to the Master data. Since Master data is only edited on the live server. Server side always takes precedence during the Sync process and updates from Server will overwrite Client side Master data.

Once a User has logged in the application stores his credentials and access rights locally and he can then continue to Login and access the application even if he is Offline. Authentication of the Username and Password is done locally.

To begin editing a Program the user will need to Load the Program from the Server. Once a program has been loaded the user can then begin editing the Supply Plan by making changes to the Consumption, Inventory and Shipments. All of the changes that the user has made are stored locally on the IndexedDb. Once the user is ready to commit his changes to the Server he then selects the Commit version option.

Please refer to [Process Flows](http://Process%20Flows) for additional information on process flows.

### Back End \- Java Spring Boot Application

The Back end is a Java Spring Boot application. The Front end connects to the Server side through the API interface. Please see the Section 5.7 and 5.8 for more details on the API.

### Front End & Back End Sync Process

**Master Data**

Master data sync pulls all the Master information that is required by users to be able to make changes to the Program.

Master data sync occurs

- Every time a user logs on  
- Every time a new program is loaded

Master data sync follows these rules:

Server side data always overwrites the Client side since new changes are only possible on the Server and Master data is never changed or added on the Local side.

**Transactional Data**

Users manually load data (to download to their computer) and commit data (upload to the server). Every time they commit data, the following occurs:

1. System checks each record in the Supply plan against what is contained on the Server  
1. Then the user is consulted to resolve conflicts on consumption, inventory and shipment list.  
1. Once all the conflicts on consumption, inventory and shipment list are resolved, QAT re-runs the QAT problem action logic on the merged data. After that QAT check for conflicts in the data for the problems that do not have the in-compliance status.

# QAT-ERP Integrations

QAT has several data integrations with Enterprise Resource Planning (ERP) systems, as shown in the below diagram. There are 3 main types of interfaces between QAT and external systems

1. **Supply plan sharing.** QAT sends supply plans to external systems. Currently QAT shares supply plans with the GHSC-PSM order management system ARTMIS, and to the GFPVAN. (See 6.1)  
1. **Catalog Updates.** QAT receives the ARTMIS product catalog, which updates the QAT planning unit details (See 6.2)  
1. **ERP Shipment linking** (See 6.3) Currently the only active ERP shipment linking interface is with ARTMIS for GHSC-PSM orders.  
- QAT receives ARTMIS Orders and Shipments  
- QAT sends QAT shipments to ARTMIS  
- QAT sends QAT Program IDs to ARTMIS

Figure 1 QAT data interfaces with ERP systems

Below are the QAT interfaces with the ARTMIS & GFPVAN applications for data exchange:

Files shared with ARTMIS are done via SFTP (xml formats); files imported from Pipeline and Quantimed are via xml- (formats dictated by each software). Files shared with GFPVAN are JSON.

Note there is no task 4 and task 5\. Note that edits are made to the ERP-linking functionality, after which time this section will updated to include those edits, including detail on fields to be shared and which items are shared as deltas versus full files. Only if the scripts below fail will QAT send an automated notification of this failure.

- [ARTMIS Sample files](http://Sample%20ARTMIS%20Feeds)

| Script name | Description | Script Path | Source folder | Source folder after completing script | Destination folder | Log Path | Schedule Time (EST) |
| :---- | :---- | :---- | :---- | :---- | :---- | :---- | :---- |
| [task1.sh](http://scripts/task1.sh) | To pull Catalog files from ARTMIS to QAT and then import them into the QAT db | /home/ubuntu/QAT/script/task1.sh | Remote server \-\> /FASP/ARTMIS | Remote server \-\> /FASP/processed | Local server \-\> /home/ubuntu/QAT/ARTMIS | /home/ubuntu/QAT/logs/ARTMIS/task1-yyyy-mm-dd.txt | 23:00 |
| [task2.sh](http://scripts/task2.sh) | To first generate the Order and Program files and then push the files from QAT to ARTMIS | /home/ubuntu/QAT/script/task2.sh | Local server \-\> /home/ubuntu/QAT/supplyPlan | Local server \-\> /home/ubuntu/QAT/supplyPlan/processed | Remote server \-\> /FASP/supplyPlan | /home/ubuntu/QAT/logs/ARTMIS/task2-yyyy-mm-dd.txt | 23:00 |
| [task3.sh](http://scripts/task3.sh) | To push Supply Plan files from QAT to ARTMIS | /home/ubuntu/QAT/script/task3.sh | Local server \-\> /home/ubuntu/QAT/supplyPlan/Artmis | Local server \-\> /home/ubuntu/QAT/supplyPlan/Artmis/processed | Remote server \-\> /FASP/supplyPlan and Sharepoint | /home/ubuntu/QAT/logs/ARTMIS/task3-yyyy-mm-dd.txt | Every 15 min |
| [task6.sh](http://scripts/task6.sh) | To push GFPVAN files from QAT to GFPVAN SFTP Server | /home/ubuntu/QAT/script/task6.sh | Local server \-\> /home/ubuntu/QAT/supplyPlan/GFPVAN | Local server \-\>/home/ubuntu/QAT/supplyPlan/GFPVAN/processed | Remote server \-\> /e2open/b2b/scpusers2/e2net/E2NETSTG/e2c0000e9a-2ed8866a49-0/in | /home/ubuntu/QAT/logs/ARTMIS/task3-yyyy-mm-dd.txt | Every 15 min |

Table 1 QAT Server Scripts

**Task1.sh** importProductCatalog.sh

1. We have a Local Directory on QAT Server `/home/ubuntu/QAT/ARTMIS` in which we will download the data from SFTP server from the `/FASP/ARTMIS`.  
1. After Downloaded we will move data in processed folder on SFTP Server `/FASP/processed`.  
1. Import the files into QAT, once they are imported they are moved to the `/home/ubuntu/QAT/ARTMIS/processed` folder  
1. Log will be generated in QAT Server `/home/ubuntu/QAT/logs/ARTMIS/task1-yyy-ymm-dd.txt`

Script Location `/home/ubuntu/QAT/script/task1.sh` (QAT Server)

**Task2.sh** exportProgramAndOrder.sh

1. Generate the Program and Order csv files in `/home/ubuntu/QAT/supplyPlan` folder  
1. We have Local path in QAT server `/home/ubuntu/QAT/supplyPlan` in which we need to transfer the csv file on SFTP server in `/FASP/supplyPlan` directory.  
1. After transferring all the files on SFTP Server all the files will move in local folder `/home/ubuntu/QAT/supplyPlan/processed`.  
1. Log will be generated in QAT Server `/home/ubuntu/QAT/logs/ARTMIS/task2-yyyy-mm-dd.txt`

**Task3.sh**

1. We have Local path in QAT server `/home/ubuntu/QAT/supplyPlan/Artmis` in which we need to transfer the json file on SFTP server in `/FASP/supplyPlan` directory.  
1. Copy of same files will be copied to sharepoint (Only Production Server)  
1. After transferring all the files on SFTP Server all the files will move in local folder `/home/ubuntu/QAT/supplyPlan/Artmis/processed`  
1. Log will be generated in QAT Server `/home/ubuntu/QAT/logs/ARTMIS/task3-yyyy-mm-dd.txt`

Script Location `/home/ubuntu/QAT/script/` (QAT Server)

**Task6.sh**

1. We have Local path in QAT server /home/ubuntu/QAT/supplyPlan/GFPVAN in which we need to transfer the text file on GFPVAN SFTP server in `/e2open/b2b/scpusers2/e2net/E2NETSTG/e2c0000e9a-2ed8866a49-0/in directory.`  
1. After transferring all the files on SFTP Server all the files will move in local folder `/home/ubuntu/QAT/supplyPlan/GFPVAN/` processed.  
1. Log will be generated in QAT Server `/home/ubuntu/QAT/logs/ARTMIS/task3-yyyy-mm-dd.txt`

Script Location `/home/ubuntu/QAT/script/`

**TO CONNECT SFTP SERVER**

sh QAT/script/sftp.sh

Prodcution Server SFTP: \<YOUR\_PROD\_SFTP\_SERVER\_IP\_ADDRESS\>

UAT Server SFTP: \<YOUR\_UAT\_SFTP\_SERVER\_IP\_ADDRESS\>

SHAREPOINT: \<YOUR\_SHAREPOINT\_URL\>

GFPVAN SFTP for UAT :bastion3.e2open.net

GFPVAN SFTP for PROD :bastion7.e2open.net

### QAT sharing Supply Plan with external systems

Below are diagrams of which supply plans are shared by QAT to external systems.

 

### QAT Catalog Updates

The QAT Catalog is created using the ARTMIS data. The Forecasting Unit, Planning Unit and Procurement Unit are all taken from ARTMIS database. If user changes any one of these data and ARTMIS pushes a new feed, then the former will be replaced by the `feed`. The non-ARTMIS data can be updated by the user on QAT. All Alternate Reporting Units are managed directly on QAT.

**Planning Unit:** The product to be planned for in QAT. It is a product with full description up to the primary packaging (e.g. bottle of 30 tablets, 10x10 blister pack, etc.)

**Forecasting Unit** is the base unit that will be used for a specified forecasting period. e.g. one tablet, one condom, one milliliter.

**Procurement Unit** is the product at item level. In other words, it is a higher-level description of the planning unit including supplier-specific attributes. This information will not be visible/selectable by QAT users but will be sent automatically from procurement management systems.

**Alternate Reporting Unit (ARU):** This is the higher-level description of the planning unit including supplier-specific attributes. This information will not be visible/selectable by QAT users but will be sent automatically from procurement management systems. The ARU is country specific.

### ERP Shipment linking (coming soon)

This section will be populated after the ERP shipment linking feature is officially launched.

| Status Mapping |   |
| :---- | :---- |
| **ARTMIS External Status Stage** | **QAT Shipment Status** |
| Cancelled | Cancelled |
| Delivered | Received |
| Order on Hold \- Pending Clarification Stage | On-hold |
| Order on Hold \- Pending Release | On-hold |
| Order on Hold \- Sourcing / Fulfillment Processing | On-hold |
| Partially Delivered | Shipped |
| Pending Clarification | Submitted |
| Pending PSM Source Final Approval | Submitted |
| Pending PSM Source Initial Approval | Submitted |
| Pending Recipient Approval | Submitted |
| Pending Release | Approved |
| Pending Shipment | Approved |
| Pending USAID Approval | Submitted |
| Shipped | Shipped |
| Sourcing / Fulfillment Processing | Submitted |

## Setting up & Managing QAT Server Side Application

Below is the current QAT service side application set up, which is the recommended set up for future implementation on other servers.

### Source code

The QAT Source code links are provided below. Each repository file in GitHub also includes source code swagger hub documentation (see section 5.7) with information of the class/method and any significant algorithm or process. The links will only work for those who have been provided access. To request access, please contact [Support@quantificationanalytics.org](mailto:Support@quantificationanalytics.org).

[https://github.com/HSS-FASP-Team/fasp-api](https://github.com/HSS-FASP-Team/fasp-api) (Server-side project for QAT that runs the API's)

[https://github.com/HSS-FASP-Team/fasp-core-ui/](https://github.com/HSS-FASP-Team/fasp-core-ui/) (Client-side project for QAT)

[https://github.com/HSS-FASP-Team/globalTables](https://github.com/HSS-FASP-Team/globalTables) (Tools to import the programs from Global Tables)

[https://github.com/HSS-FASP-Team/ConvertPipelineToJson](https://github.com/HSS-FASP-Team/ConvertPipelineToJson) (Tool to convert Pipeline to JSON)

### Database Design

See Annex 2: Entity-Relationship Diagrams for the diagrams below.

- Product  
- Inventory, Consumption, Orders, Shipments  
- Program  
- Application

Note that the indexDB which is saved on each user's computer is a subset of this larger database design. All masters data is loaded by default (restricted by access rights), and users select specific program-versions to load.

### Server Design

The below diagram explains the setup that is the suggested setup for the QAT application. The current set up is with Amazon Web Services (AWS) . For access to the login for this, please contact [Support@quantificationanalytics.org](mailto:Support@quantificationanalytics.org).

Figure 2: Relational Database Service (RDS) and Virtual Private Cloud (VPC)

This is the suggested setup the user can choose to downgrade this as per requirements. On a minimum level ,the following components are required

a. Load balancer or WebServer  
b. Application Server  
c. Database Server

### Operating system requirements

a. Application Server

The Application server requires a Linux based server preferably Ubuntu with at least ver 18.04LTS or higher.

b. Database Server

The Database server needs MySql v 5.7

### Other Software

a. Application Server

i. Java JDK ver 11 (Command to install is as under: `sudo apt-get install openjdk-11-jdk-headless`)  
ii. Apache WebServer

sudo apt-get install apache2

iii. Node JS version 8.10  
iv. PM2: is a production process manager for Node.js applications with a built-in load balancer. It allows you to keep applications alive forever, to reload them without downtime and to facilitate common system admin tasks.

### Setup process

1. Application Server  
   i. Apache server

To setup Apache server use the following commands

sudo apt-get install apache2

Copy the SSL Certificates into a folder called `/etc/apache2/ssl/`

Create a file called `www.quantificationanalytics.org.conf` in the following folder `/etc/apache2/sites-available/` with the following contents

\<VirtualHost \*:80\>

  ServerName quantificationanalytics.org

  ServerAlias www.quantificationanalytics.org

  ServerAdmin info@altius.cc

  ErrorLog /var/log/apache2/qat\_error.log

  \# Possible values include: debug, info, notice, warn, error, crit,

  \# alert, emerg.

  LogLevel warn

  RewriteEngine On

  RewriteRule (.\*) https://%{HTTP\_HOST}%{REQUEST\_URI}

  CustomLog /var/log/apache2/qat\_access.log combined

\</VirtualHost\>

\<VirtualHost \*:443\>

  ServerName quantificationanalytics.org

  ServerAlias www.quantificationanalytics.org

  ServerAdmin info@altius.cc

  ErrorLog /var/log/apache2/qat\_error.log

  \# Possible values include: debug, info, notice, warn, error, crit,

  \# alert, emerg.

  LogLevel warn

  CustomLog /var/log/apache2/qat\_access.log combined

  ProxyRequests Off

  ProxyPreserveHost On

  ProxyPass / http://localhost:4202/

  SSLEngine On

  SSLCertificateFile /etc/apache2/ssl/WWW.QUANTIFICATIONANALYTICS.ORG.crt

  SSLCertificateKeyFile /etc/apache2/ssl/www.quantificationanalytics.org.txt

  SetEnvIf User-Agent ".\*MSIE.\*" nokeepalive ssl-unclean-shutdown

  Header always set Strict-Transport-Security "max-age=63072000;"

  Header always unset X-Frame-Options

  Header set X-Frame-Options "SAMEORIGIN"

  Header always set X-Content-Type-Options nosniff

  Header set Feature-Policy: "fullscreen 'self'"

  Header set Referrer-Policy: "no-referrer"

\</VirtualHost\>

Create a file called `api.quantificationanalytics.org.conf` in the following folder `/etc/apache2/sites-available/` with the following contents

\<VirtualHost \*:80\>

  ServerAlias api.quantificationanalytics.org

  ServerAdmin info@altius.cc

  ErrorLog /var/log/apache2/qatapi\_error.log

  \# Possible values include: debug, info, notice, warn, error, crit,

  \# alert, emerg.

  LogLevel warn

  RewriteEngine On

  RewriteRule (.\*) https://%{HTTP\_HOST}%{REQUEST\_URI}

  CustomLog /var/log/apache2/qatapi\_access.log combined

\</VirtualHost\>

\<VirtualHost \*:443\>

  ServerAlias api.quantificationanalytics.org

  ServerAdmin info@altius.cc

  ErrorLog /var/log/apache2/qatapi\_error.log

  \# Possible values include: debug, info, notice, warn, error, crit,

  \# alert, emerg.

  LogLevel warn

  CustomLog /var/log/apache2/qatapi\_access.log combined

  ProxyRequests Off

  ProxyPreserveHost On

  ProxyPass / ajp://localhost:8109/

  SSLEngine On

  SSLCertificateFile /etc/apache2/ssl/API.QUANTIFICATIONANALYTICS.ORG.crt

  SSLCertificateKeyFile /etc/apache2/ssl/api.quantificationanalytics.org.txt

  SSLCertificateChainFile /etc/apache2/ssl/DV\_USERTrustRSACertificationAuthority.crt

  SetEnvIf User-Agent ".\*MSIE.\*" nokeepalive ssl-unclean-shutdown

\</VirtualHost\>

To enable the modules required for Apache to work execute the following

sudo a2enmod proxy proxy\_ajp proxy\_http rewrite ssl

To enable the sites you need to run the following command

sudo a2ensite www.quantificationanalytics.org.conf

sudo a2ensite api.quantificationanalytics.org.conf

To restart the apache2 service and activate your new settings run the following command.

sudo systemctl restart apache2.service

b. Node JS and PM2 c. API Application

To Install the application and run it on the Server download the compiled QAT.war file on to the Server /home/ubuntu/qatApi folder

Make sure the QAT.war file is executable

Create a service file in the `/etc/systemd/system/QATAPI.service` with the following content

\[Unit\]

Description=QAT API

\[Service\]

User=nobody

\# The configuration file application.properties should be here:

WorkingDirectory=/home/ubuntu/qatApi

ExecStart=/usr/bin/java \-Xmx256m \-jar QATAPI.jar

SuccessExitStatus=143

\# TimeoutStopSec=10

\# Restart=on-failure

\# RestartSec=5

\[Install\]

WantedBy=multi-user.target

Make sure that service file has 755 rights

Then you need to let run `sudo systemctl daemon-reload` to reload the daemon services

You can now use the following commands to start, stop or restart the application

sudo systemctl start QATAPI.service

sudo systemctl stop QATAPI.service

sudo systemctl status QATAPI.service

sudo systemctl restart QATAPI.service

### List of API's

The [QAT API Master](https://app.swaggerhub.com/apis/Altius/QAT/1.09\&sa=D\&source=hangouts\&ust=1613216661474000\&usg=AFQjCNFxS6ivBEXC2SMQWoOxb5eYzRGwTA) is available for reference. Refer to the Index html file in the zip for a list of the APIs:

[https://app.swaggerhub.com/apis/Altius/QAT/1.12](https://app.swaggerhub.com/apis/Altius/QAT/1.12)

Updated APIS list for Mod-2:

[https://app.swaggerhub.com/apis/Altius/QAT/2.12](https://app.swaggerhub.com/apis/Altius/QAT/2.12)

### How to call an API

API stands for **A**pplication **P**rogramming **I**nterface. The most important part of this name is "interface", because an API essentially talks to a program for you.

To call an API on QAT you need to call the end point of the API and you need to pass the JWT \[\[1\]\](\#footnote-1Key Value pair where the Key is "Authorization" and the Token must be prefixed with the following string "Bearer " JSON Web Token (JWT) is a means of representing claims to be transferred between two parties. The claims in a JWT are encoded as a JSON object that is digitally signed using JSON Web Signature (JWS) and/or encrypted using JSON Web Encryption (JWE).

Details on the end points and the parameters that you need to pass and that you will receive from the API's are mentioned in the API list provided above.

As an example, to generate the JWT authorization token you need to call the `https://www.quantificationanalytics.org/authenticate` endpoint. The Username and Password should be passed to the end point as part of the Body as a JSON object.

Sample JSON

{

  "username": "sombody@somedomain.com",

  "password": "somepassword"

}

This will return you the JWT authorization token that you can use in the subsequent API calls. The JWT is valid for a period of 6hrs from the time of issue.

### React Specifications

1. React JS Version : 16.8.6  
1. NPM Version : 6.14.4  
1. Nodejs Version : 12.16.1  
1. Html-webpack-plugin Version : 4.2.0  
1. Workbox-webpack-plugin Version : 5.1.2  
1. Babel-loader Version : 8.1.0  
1. @babel/core Version : 7.9.0  
1. I18next Version : 19.3.3  
1. React-dom Version : 16.8.6  
1. Jexcel Version : 4.4.1  
1. React JS Overview

React is a library for building composable user interfaces, it supports and encourages the creation of reusable UI components, which presents data that changes over time by using declarative syntax.

It uses a concept called Virtual Dom, that selectively renders subtrees of nodes upon state changes. It does the least amount of DOM manipulation possible in order to keep your components up to date.React finds out what changes have been made, and changes only what needs to be changed.

1. Indexed DB:

IndexedDB is a large-scale, NoSQL storage system. It lets you store just about anything in the user's browser. In addition to the usual search, get, and put actions, IndexedDB also supports transactions. Here is the definition of IndexedDB on MDN:

"IndexedDB is a low-level API for client-side storage of significant amounts of structured data, including files/blobs. This API uses indexes to enable high performance searches of this data. While DOM Storage is useful for storing smaller amounts of data, it is less useful for storing larger amounts of structured data. IndexedDB provides a solution."

Each IndexedDB database is unique to an origin (typically, this is the site domain or subdomain), meaning it cannot access or be accessed by any other origin. Data storage limits are usually quite large, if they exist at all, but different browsers handle limits and data eviction differently

Browsers supported :Chrome,Firefox,Opera,Safari

1. Local Storage:

The Local Storage is an internal database created into the browser, which you can use to save data in a key-value format. Most popular and commonly used browsers like Chrome, Firefox and Safari all support Local Storage. The data stored in local storage has no expiration date, so it will persist over the closed browser window and session.

Local storage has a very simple set, retrieve and remove API, and the key-value pair is always of string type.

1. Webpack and Workbox:

Webpack is a popular module bundling system built on top of Node. js. It can handle not only combination and minification of JavaScript and CSS files, but also other assets such as image files (spriting) through the use of plugins.

**Workbox** is the successor to sw-precache and sw-toolbox . It is a collection of libraries and tools used for generating a service worker, and for precaching, routing, and runtime-caching. **Workbox** also includes modules for easily integrating background sync and Google Analytics into your service worker.

1. Service Worker:

A service worker is a script that your browser runs in the background, separate from a web page, opening the door to features that don't need a web page or user interaction.It's a JavaScript Worker, so it can't access the DOM directly. Instead, a service worker can communicate with the pages it controls by responding to messages sent via the postMessage interface, and those pages can manipulate the DOM if needed.

Service worker is a programmable network proxy, allowing you to control how network requests from your page are handled.

It's terminated when not in use, and restarted when it's next needed, so you cannot rely on global state within a service worker's onfetch and onmessage handlers. If there is information that you need to persist and reuse across restarts, service workers do have access to the IndexedDB API.

The reason this is such an exciting API is that it allows you to support offline experiences.

Service workers are supported by Chrome, **Firefox** and Opera and Safari

1. I18next

i18next is an i18n framework written in and for JavaScript. It provides the standard i18n features of interpolation, formatting, and handling plurals and context.

1. App Installation Feature :

Browsers Supported : Chrome

### React App Installation steps

Open our Terminal and paste the below command.

$ curl \-sL https://deb.nodesource.com/setup\_12.x | sudo \-E bash \-

$ sudo apt-get install \-y nodejs

Update npm to latest version using below command

sudo npm install npm@latest \-g

### Running QAT Application

To run a QAT application on your local machine download it from git repository. Download the file [https://github.com/FASP-QAT/fasp-api/blob/master2/docs/QATFolders.7z](https://github.com/FASP-QAT/fasp-api/blob/master2/docs/QATFolders.7z) and expand it into the QAT Home folder. The default Home folder is /home/ubuntu/QAT. If you want to change the Home folder please make a change in the application.properties file

qat.homeFolder=/home/ubuntu/QAT

In the properties folder inside the QAT directory you will need to follow instructions and fill in the parameters specific to your instance.

Enter the project directory and execute npm install and npm run dev

$ cd fasp-core-ui

$ npm install

$ npm run dev

It starts the React application on port 4204\. Open new tab on browser with below address

[http://localhost:4202](http://localhost:4202)

### Running R based reports

Please follow the below steps to install R application required for running ARIMA based reports.

$ sudo apt update

$ sudo apt install libsodium-dev

$ sudo apt-get install libcurl4-openssl-dev

$ sudo apt install r-base

After installing R application type R in command prompt to launch the console of R application as given below.

$ R

Once you enter the console of R application you need to install Plumber and forecast packages. Given below are the steps.

install.packages("plumber")

install.packages("forecast")

You can run the R application with below command.

plumber::plumb(file='/root/Documents/GitRepo/Rprojects/api\_arima.r')$run(host="0.0.0.0", port=8000)

After running R application go to Postman application and enter below line in post column and enter the details.

[http://server-ipaddress:8000/tes](http://server-ipaddress:8000/tes)

## Integration with Country Dashboard {#integration-with-country-dashboard}

A new functionality that allows Countries to pull completed Supply Plans for one Program at a time has just been introduced. The full documentation for this Integration is available in the following [file](https://github.com/FASP-QAT/fasp-api/blob/a271980e12637d6a31f4d87560df85a88789ee6e/docs/API%20Documentation%20for%20Dashboard%20integration%20v1.31.pdf)

## Annex 1: Business & Technical Requirements

| No. | Scope Summary | Scope Description |
| :---- | :---- | :---- |
| BR01 | Data: Commodity Information | Shall store data on list of health commodities, with an ID \#, dosage, pack size, quantity, weight, package, volume, price (by different funders) etc |
| BR02 | Data: Commodity Information | Shall allow for grouping of key products (e.g. "male condoms") |
| BR03 | Data: Commodity Information | Shall store data on health commodity regimens |
| BR04 | Data: Commodity Information | Shall store data on WHO recommended regimens, and shall receive updates when these shift Country Forecasting |
| BR06 | Data: Commodity Information | Shall store information on commodity volume, weight, etc. |
| BR07 | Data: Commodity Information | Shall store country Standard Treatment Guidelines, and relationships between medicines, regimens, and commodity consumption |
| BR08 | Data: Commodity Information | Shall store facility information (with GLN codes) |
| BR09 | Reporting & System Interactions | Shall accept import from Quantimed for historical data |
| BR10 | Data: Budget | Shall store country, donor funding disbursement schedules |
| BR11 | Data: Budget | Shall store total, commodity, and TO budgets |
| BR12 | Data: Budget | Shall store price data across donors |
| BR13 | Data: Budget | Shall store other order costs, shipping, etc |
| BR14 | Data: Budget | Shall identify and store funding gaps |
| BR15 | Data: Inventory | Shall store current inventory reported level, as well as inventory projections (most recent inventory data point \+ shipments \- consumption \+adjustments) by date |
| BR16 | Data: Inventory | Shall store inventory data at the sub national (facility of other levels) |
| BR17 | Data: Inventory | Shall store inventory expiry data and projections unique per product (and batch) |
| BR18 | Data: Inventory | Shall store warehouse capacity data |
| BR19 | Data: Inventory | Shall make programmatic recommendations for max/min levels of stock |
| BR20 | Data: Inventory | Shall store desired max/ min levels of stock |
| BR21 | Data: Inventory | Shall have capacity for manual stock adjustment notes (expiry, shifting, loss, etc) |
| BR22 | Data: Inventory | Shall distinguish between on hand inventory, sales order inv, and inventory in transit |
| BR23 | Supply Planning Capability | Shall connect with donor system, order management system to id status, delivery date, updated cost etc of placed shipment |
| BR24 | Supply Planning Capability | Shall use forecast (current month \+ next two months) to project months of stock figure |
| BR25 | Supply Planning Capability | Shall store donor order lead times by products and trigger order reminders |
| BR26 | Supply Planning Capability | Shall indicate whether a shipment is intended to be "Targeted Local Procurement" (TLP) and store separate lead times, etc |
| BR27 | Supply Planning Capability | Shall recommend shipments by date, quantity and donor to maintain inventory between min and max stock level(with options for manual update) |
| BR28 | Supply Planning Capability | Shall assist country in dividing up/staggering large shipments that may be logistically difficult to deliver in same order. (Shall flag large shipments, recommend how to split up) |
| BR29 | Supply Planning Capability | Shall recommend shipments by date, quantity and donor using inventory optimization or economic order quantity principles, lead times and standard deviation of demand |
| BR30 | Supply Planning Capability | Shall recommend inventory holding levels (max and min, safety stock and order to) based on lead time, forecast and consumption standard deviation, expiry, etc |
| BR31 | Supply Planning Capability | Shall utilize optimization techniques to recommend shipment allocation across donors based on funding availability and price differences. |
| BR32 | Supply Planning Capability | Shall allow comparison of supply plan scenarios : inventory and price scenarios between differing recommended shipments |
| BR33 | Supply Planning Capability | Shall allow for "what-if" scenarios if shipment expected quantity and timeframes shift |
| BR34 | Supply Planning Capability | Shall store comments for "TBD" and "planned" shipments in near term, and allow country to add context regarding funding assignment and/or order placement timeframe |
| BR35 | Supply Planning Capability | Shall consider "restrictions" such as lead times \[frozen planning period\] and donor budgets, impending expiry, etc \- and alert user |
| BR36 | Supply Planning Capability | Shall consider supply planning at the sub national (/facility) level, with an understanding of lead times from natoinal medical store to sub national level |
| BR37 | Reporting & System Interactions | Shall be interoperable with forecasting system |
| BR38 | Reporting & System Interactions | Shall be interoperable with donor order management system (e.g. ARTMIS), if possible |
| BR39 | Reporting & System Interactions | Shall document key risks in Notes |
| BR40 | Reporting & System Interactions | Shall report on metrics such as planned shipments, & projected stock levels |
| BR41 | Reporting & System Interactions | Shall report on measures such as cost of inventory |
| BR42 | Reporting & System Interactions | Shall interface with PPMR, PPMR(M), PPMR(A), and existing collaborative planning tools |
| BR43 | Reporting & System Interactions | Shall provide cost based reporting, and identify potential budget shortfalls |
| BR44 | Reporting & System Interactions | Shall report on metrics such as delivery, inventory turns, slow-moving/expired inventory |
| BR45 | Reporting & System Interactions | Shall generate Supply Planning Action reports, which list dates recommended to place ROs based on specific (product, country, donor) lead times |
| BR46 | Reporting & System Interactions | Shall allow reporting for supply planning, warehouse and transportation functions using commodity weight and volume |
| BR47 | Reporting & System Interactions | Shall allow for transparency of calculations by demonstrating a visual depiction of forecasting and supply planning data flow |
| BR48 | Reporting & System Interactions | Shall accept imports from PipeLine for historical data |
| TR1 | Data Mapping | System shall have the ability to map different Product Catalogs / Hierarchies against the USAID catalog |
| TR10 | API/Integrations | System shall the ability to export an entire model (Forecast/Supply Plan) by version |
| TR12 | API/Integrations | System shall have the ability to import/export in multiple formats (XML, CSV, JSON, PDF) |
| TR13 | Authentication | If a multi-user system, shall have the ability to provision individual accounts for access |
| TR14 | Authentication | If a multi-user system, shall have the ability to connect authentication sources with external sources (i.e. Active Directory, LDAP, etc.) |
| TR15 | Authorization | If a multi-user system, shall have the ability to separate account access by roles (i.e. groups) |
| TR16 | Authorization | If a multi-user system, shall have the ability to assign roles to capabilities (i.e. read only, read-write, create) |
| TR17 | Audit | System will maintain an audit log of login, changes to permissions and changes to models |
| TR2 | Data Mapping | System shall have the ability to update Reference Mapping Data without disrupting existing models |
| TR21 | Platform / Tech Stack | System shall have support customizable language localization |
| TR22 | Scalability & Performance | System shall demonstrate user responsiveness regardless of location in the world |
| TR3 | Data Mapping | System shall have the ability to version Reference Data |
| TR4 | Scheduling | System shall have the ability to batch import/export/jobs scheduled and monitored |
| TR5 | Scheduling | System shall have the ability to detect if an import of data is executed twice |
| TR6 | API/Integrations | System shall the ability to import data from external systems |
| TR7 | API/Integrations | System shall have an API that respects the applications security model |
| TR8 | API/Integrations | System shall have a method for extensibility that allows for customization without modifying core code |
| TR9 | API/Integrations | System shall have the ability to export change sets |

Note: BR5 was removed during the development process.

# Annex 2: Entity-Relationship Diagrams

(*updated July 26th, 2022*)

Please see the corresponding png files in the zipped folder for the ten [Entity-Relationship Diagrams](http://Database%20ER%20Diagrams).

Figure 3: Example of an Entity-Relationship Diagram

# Annex 3: Data Dictionary

(*updated on July 26th 2022*)

Please see the corresponding excel file in the zipped folder for the [Data Dictionary v2.9](http://Data%20dictionary%20QAT%20SP%20v2.9.xlsx).

**Note:** while this data dictionary has been created for the supply plan output that's sent to the GFPVAN & ARTMIS \- the fields cover all data fields in the broader database structure.

Figure 4: Example of the Data Dictionary

1. JSON Web Token (JWT) is a means of representing claims to be transferred between two parties. The claims in a JWT are encoded as a JSON object that is digitally signed using JSON Web Signature (JWS) and/or encrypted using JSON Web Encryption (JWE). [↑](\#footnote-ref-1)

