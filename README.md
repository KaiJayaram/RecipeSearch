# Recipe Search Android Mobile App
Used Selenium and ChromeDriver as well as python requests and beautiful soup libraries to scrape online recipe data 
including recipe name, ingredients, directions, and nutritional information. The Code I used to do this can be found
in the python files.

# ScrapeTest.py:
  Here I used Selenium with chrome driver to automate a webbrowser in order to collect a list of links to individual recipes.
  This list is stored in linksorigin.txt
# RequestTest.py:
  In This one I used the python requests and Beautiful Soup libraries in order to scrape data from the collected list of links
  and upload this data into the MYSQL database that can be found in the recipes folder.
 
In the app folder you will find all of the code for creating the UI for the app that allows the user to access the database
This was made using android studio.

# recipeConnection.php:
  This php file is used in the app code to create a connection to the database
  
# recipeData.php:
  this php file is the one used for all look ups into the database. It is run from the code in the app
  to allow the user to search for their desired recipes based on name, ingredients, or nutritional informaiton.



