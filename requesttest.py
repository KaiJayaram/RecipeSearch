import requests
from bs4 import BeautifulSoup
import MySQLdb;


# Open database connection
db = MySQLdb.connect(host="localhost",user="root",passwd='test',db="recipes");

# prepare a cursor object	
cursor = db.cursor();

i = 0;
with open("linksorigin.txt",'r') as f:
	for line in f:
		fail = False;
		i=i+1;
		page = requests.get(line);
		soup = BeautifulSoup(page.content, 'html.parser')

		#find ingredients
		ingr = soup.find_all('section', class_='recipe-ingredients-section col-xs-12');
		ingr_str = "";
		if len(ingr) > 0:
			ingred = ingr[0].find_all('ul');
			for item in ingred:
				ingr_str += item.get_text();
		else:
			print('ingr')
			fail = True;
				
		#find steps
		steps = "";
		step = soup.find_all('section', class_='recipe-method-section mobile-collapsed col-xs-12 col-sm-8');
		if len(step) > 0:
			li = step[0].find_all('ul');
			for item in li:
				steps += item.get_text();
			step_str = steps.strip();
		else:
			print('steps')
			fail = True;

		#find nutrition
		nutrition = "";
		nut_list = soup.find_all('section', class_='nutrition-collapsible-text-box');
		if len(nut_list) > 0:
			nutri = nut_list[0].find_all('ul');
			for item in nutri:
				nutrition += item.get_text();
			nutrition = nutrition.strip();
		else:
			print('nutrition')
			fail = True;

		#find name
		name = soup.find_all('div', class_='col-xs-12');
		if len(name) > 0:
			name_str = name[0].get_text();
		else:
			print('name')
			fail = True;
		
		#find image links
		img = soup.find_all('img', class_='img-responsive main-icon');
		if len(img) > 0:
			img_str = img[0]['src'];
		else:
			print('link')
			fail = True;


		sql = """INSERT INTO recipe_data(Name,
							Steps, Ingredients, Nutrition, IMG_URL)
							VALUES ("%s", "%s", "%s", "%s", "%s");""" %(name_str, step_str, ingr_str, nutrition, img_str);
		try:
			if fail==False:
				# Execute the SQL command
				cursor.execute(sql);
				# Commit your changes in the database
				db.commit();
				print(i);
			else:
				print('info not found');
		except:
			# Rollback in case there is any error
			db.rollback();
			print('fail');
	
db.close();
