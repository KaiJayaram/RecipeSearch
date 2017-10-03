from selenium import webdriver
path_to_chromedriver = 'C:\Users\liqhtninq\Documents\Java,Android Plugins\chromedriver_win32\chromedriver.exe' # change path as needed
browser = webdriver.Chrome(executable_path = path_to_chromedriver)
f = open("links.txt", "w")
for j in range(2,996):
	url = 'http://www.taste.com.au/recipes/collections/nutrition-information?page=%d&q=&sort=az'%(j)
	browser.get(url)
	for i in range(1,21):
		xpath = '/html/body/div[2]/div[2]/main/div[2]/div/div/ol/li[%d]/article/figure/a' %(i)
		link = browser.find_element_by_xpath(xpath)
		f.write(link.get_attribute('href') + '\n')
f.close()
browser.close()