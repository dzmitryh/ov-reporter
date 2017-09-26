# ov-report-generator

* Script does autologin into your account with provided credentials
* Selects the latest reporting month
* Unchecks all weekend trip transactions
* And downloads the final report

## Prerequisites
* Java 1.8 +
* Maven
* Downloaded chrome driver --> https://sites.google.com/a/chromium.org/chromedriver/downloads

## Installation and run
* Define 3 environment variables: 
    * export ov_username="your-ov-username"
    * export ov_password="your-ov-password"
    * export chrome_driver_path="path-to-your-chrome-driver" | for instance --> <i>/Applications/chromedriver</i>
* Go into cloned project  
* Build project: `mvn clean install`
* Run it `java -jar target/ovreporter-jar-with-dependencies.jar`
* Check out your download folder! Hooray! Enjoy! ;)