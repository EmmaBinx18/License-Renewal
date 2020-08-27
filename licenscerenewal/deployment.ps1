# Heroku command line interface : https://cli-assets.heroku.com/heroku-x64.exe

git init | git add . | git commit -m "Deployment" | heroku login | heroku git:remote -a license-renewal | git push heroku master -f