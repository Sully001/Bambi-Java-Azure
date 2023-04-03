<div style="background-color: white;">
  <img src="bambi-photos/Bambi_Shoes_Logo_no-bg.png" style="background-color: white;">
</div>
<h1 align="center">Hi 👋, we're Bambi Admin</h1>
<h3 align="center">Bambi Inventory Management System</h3>

<h1>Description</h1>
Bambi Admin is a functional, profesional inventory managemnt system designed to make running a business easy!
The backend of this site was built with Java (Spring Boot). The front end of the site was built using HTML, CSS, Bootstrap and Javascript. Our database uses MySQL and it is hosted on Azure.

-------------------------------------------------------------------------------------------------

<h3 align="left">Languages and Tools:</h3>
<p align="left"> <a href="https://azure.microsoft.com/en-in/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/microsoft_azure/microsoft_azure-icon.svg" alt="azure" width="40" height="40"/> </a> <a href="https://getbootstrap.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/bootstrap/bootstrap-plain-wordmark.svg" alt="bootstrap" width="40" height="40"/> </a> <a href="https://www.chartjs.org" target="_blank" rel="noreferrer"> <img src="https://www.chartjs.org/media/logo-title.svg" alt="chartjs" width="40" height="40"/> </a> <a href="https://www.w3schools.com/css/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/css3/css3-original-wordmark.svg" alt="css3" width="40" height="40"/> </a> <a href="https://git-scm.com/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/git-scm/git-scm-icon.svg" alt="git" width="40" height="40"/> </a> <a href="https://www.w3.org/html/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/html5/html5-original-wordmark.svg" alt="html5" width="40" height="40"/> </a> <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a> <a href="https://www.mysql.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/mysql/mysql-original-wordmark.svg" alt="mysql" width="40" height="40"/> </a> <a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/> </a> </p>

-------------------------------------------------------------------------------------------------

<h2>Website Features</h2>
A user will be Admin or a Manage Editor, Admin being the super user with the ability to create new Manage Editors. This is a security feature to keep your sensitive data confidential. 

<ul>
  <li>Inventory alert system: If an item goes out of stock, or under a specified threshold this must be automatically
communicated to the user in an effective and friendly way</li>
  <li>The application should be able to generate reports that will be displayed to Admin staff about current stock
levels as well as incoming and outgoing orders for all products</li>
  <li>The system will allow authorized users to search, filter and view the status of selected products and orders.</li>
  <li>The system will allow authorized users to initiate and process an incoming order in a friendly way. Following
this entry, the stock level will be automatically updated in the database
</li>
  <li>The system will allow users to add stock to the database</li>
</ul>

-------------------------------------------------------------------------------------------------

<h1>Setup Requirements</h1>
<ul>
  <li>JDK Version : 17</li>
  <li>XAMPP (For any OS)</li>
  <li>Chrome Version : 112.0.5615.45</li>
  <li>Screen Resolution : 1920 x 1080</li>
</ul>


<h3>Working locally</h3>
<ol>
  <li>Make sure that XAMPP is installed on your local machine</li>
  <li>Make sure that you have an appropraute IDE installed (Intellij IDEA, Visual Studio Code, Ecllispe)</li>
  <li>Clone this Github repo</li>
  <li>Change the filename of (example.env) => (env)</li>
  <li>
    Make sure that your database variables are correct.
        <ul>
            <li>DB_CONNECTION=mysql</li>
            <li>DB_HOST=localhost</li>
            <li>DB_PORT=3306</li>
            <li>DB_DATABASE=bambi_site</li>
            <li>DB_USERNAME=root</li>
            <li>DB_PASSWORD=''</li>
        </ul>
  </li>
</ol>

1. `Open Bambi-Java-Azure`
2. Run BambiadminApplication
3. Open the website in your browser `http://localhost:8080/`
