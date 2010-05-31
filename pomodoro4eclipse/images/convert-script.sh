#!/bin/bash
# Large:
cp 0.svg 0RL.svg
cp 0.svg 0GL.svg
cp 0.svg 0YL.svg
cp 1.svg 1RL.svg
cp 1.svg 1GL.svg
cp 1.svg 1YL.svg
cp 2.svg 2RL.svg
cp 2.svg 2GL.svg
cp 2.svg 2YL.svg
cp 3.svg 3RL.svg
cp 3.svg 3GL.svg
cp 3.svg 3YL.svg
cp 4.svg 4RL.svg
cp 4.svg 4GL.svg
cp 4.svg 4YL.svg
cp 5.svg 5RL.svg
cp 5.svg 5GL.svg
cp 5.svg 5YL.svg
cp 6.svg 6RL.svg
cp 6.svg 6GL.svg
cp 6.svg 6YL.svg
cp 7.svg 7RL.svg
cp 7.svg 7GL.svg
cp 7.svg 7YL.svg
cp 8.svg 8RL.svg
cp 8.svg 8GL.svg
cp 8.svg 8YL.svg
cp 9.svg 9RL.svg
cp 9.svg 9GL.svg
cp 9.svg 9YL.svg
cp d.svg dRL.svg
cp d.svg dGL.svg
cp d.svg dYL.svg
find . -name '*YL.svg' -exec sed -i 's/#FF0000/#C8C800/g' {} \;
find . -name '*GL.svg' -exec sed -i 's/#FF0000/#00AC00/g' {} \;
find . -name '*YL.svg' -exec sed -i 's/#DDDDDD/#000000/g' {} \;
find . -name '*GL.svg' -exec sed -i 's/#DDDDDD/#000000/g' {} \;
find . -name '*RL.svg' -exec sed -i 's/#DDDDDD/#000000/g' {} \;
find . -name '*YL.svg' -exec sed -i 's/rgb(255, 255, 255)/rgb(0, 0, 0)/g' {} \;
find . -name '*GL.svg' -exec sed -i 's/rgb(255, 255, 255)/rgb(0, 0, 0)/g' {} \;
find . -name '*RL.svg' -exec sed -i 's/rgb(255, 255, 255)/rgb(0, 0, 0)/g' {} \;
inkscape 0RL.svg -e 0RL.png -w 96 -b '#000000' -y 1.0
inkscape 0YL.svg -e 0YL.png -w 96 -b '#000000' -y 1.0
inkscape 0GL.svg -e 0GL.png -w 96 -b '#000000' -y 1.0
inkscape 1RL.svg -e 1RL.png -w 96 -b '#000000' -y 1.0
inkscape 1YL.svg -e 1YL.png -w 96 -b '#000000' -y 1.0
inkscape 1GL.svg -e 1GL.png -w 96 -b '#000000' -y 1.0
inkscape 2RL.svg -e 2RL.png -w 96 -b '#000000' -y 1.0
inkscape 2YL.svg -e 2YL.png -w 96 -b '#000000' -y 1.0
inkscape 2GL.svg -e 2GL.png -w 96 -b '#000000' -y 1.0
inkscape 3RL.svg -e 3RL.png -w 96 -b '#000000' -y 1.0
inkscape 3YL.svg -e 3YL.png -w 96 -b '#000000' -y 1.0
inkscape 3GL.svg -e 3GL.png -w 96 -b '#000000' -y 1.0
inkscape 4RL.svg -e 4RL.png -w 96 -b '#000000' -y 1.0
inkscape 4YL.svg -e 4YL.png -w 96 -b '#000000' -y 1.0
inkscape 4GL.svg -e 4GL.png -w 96 -b '#000000' -y 1.0
inkscape 5RL.svg -e 5RL.png -w 96 -b '#000000' -y 1.0
inkscape 5YL.svg -e 5YL.png -w 96 -b '#000000' -y 1.0
inkscape 5GL.svg -e 5GL.png -w 96 -b '#000000' -y 1.0
inkscape 6RL.svg -e 6RL.png -w 96 -b '#000000' -y 1.0
inkscape 6YL.svg -e 6YL.png -w 96 -b '#000000' -y 1.0
inkscape 6GL.svg -e 6GL.png -w 96 -b '#000000' -y 1.0
inkscape 7RL.svg -e 7RL.png -w 96 -b '#000000' -y 1.0
inkscape 7YL.svg -e 7YL.png -w 96 -b '#000000' -y 1.0
inkscape 7GL.svg -e 7GL.png -w 96 -b '#000000' -y 1.0
inkscape 8RL.svg -e 8RL.png -w 96 -b '#000000' -y 1.0
inkscape 8YL.svg -e 8YL.png -w 96 -b '#000000' -y 1.0
inkscape 8GL.svg -e 8GL.png -w 96 -b '#000000' -y 1.0
inkscape 9RL.svg -e 9RL.png -w 96 -b '#000000' -y 1.0
inkscape 9YL.svg -e 9YL.png -w 96 -b '#000000' -y 1.0
inkscape 9GL.svg -e 9GL.png -w 96 -b '#000000' -y 1.0
inkscape dRL.svg -e dRL.png -w 96 -b '#000000' -y 1.0
inkscape dYL.svg -e dYL.png -w 96 -b '#000000' -y 1.0
inkscape dGL.svg -e dGL.png -w 96 -b '#000000' -y 1.0
convert -crop 48x160+24+0 dRL.png dRL.png
convert -crop 48x160+24+0 dGL.png dGL.png
convert -crop 48x160+24+0 dYL.png dYL.png

#Small

cp 0.svg 0RM.svg
cp 0.svg 0GM.svg
cp 0.svg 0YM.svg
cp 1.svg 1RM.svg
cp 1.svg 1GM.svg
cp 1.svg 1YM.svg
cp 2.svg 2RM.svg
cp 2.svg 2GM.svg
cp 2.svg 2YM.svg
cp 3.svg 3RM.svg
cp 3.svg 3GM.svg
cp 3.svg 3YM.svg
cp 4.svg 4RM.svg
cp 4.svg 4GM.svg
cp 4.svg 4YM.svg
cp 5.svg 5RM.svg
cp 5.svg 5GM.svg
cp 5.svg 5YM.svg
cp 6.svg 6RM.svg
cp 6.svg 6GM.svg
cp 6.svg 6YM.svg
cp 7.svg 7RM.svg
cp 7.svg 7GM.svg
cp 7.svg 7YM.svg
cp 8.svg 8RM.svg
cp 8.svg 8GM.svg
cp 8.svg 8YM.svg
cp 9.svg 9RM.svg
cp 9.svg 9GM.svg
cp 9.svg 9YM.svg
cp d.svg dRM.svg
cp d.svg dGM.svg
cp d.svg dYM.svg
find . -name '*YM.svg' -exec sed -i 's/#FF0000/#C8C800/g' {} \;
find . -name '*GM.svg' -exec sed -i 's/#FF0000/#00AC00/g' {} \;
find . -name '*YM.svg' -exec sed -i 's/#DDDDDD/#000000/g' {} \;
find . -name '*GM.svg' -exec sed -i 's/#DDDDDD/#000000/g' {} \;
find . -name '*RM.svg' -exec sed -i 's/#DDDDDD/#000000/g' {} \;
find . -name '*YM.svg' -exec sed -i 's/rgb(255, 255, 255)/rgb(0, 0, 0)/g' {} \;
find . -name '*GM.svg' -exec sed -i 's/rgb(255, 255, 255)/rgb(0, 0, 0)/g' {} \;
find . -name '*RM.svg' -exec sed -i 's/rgb(255, 255, 255)/rgb(0, 0, 0)/g' {} \;
inkscape 0RM.svg -e 0RM.png -w 48 -b '#000000' -y 1.0
inkscape 0YM.svg -e 0YM.png -w 48 -b '#000000' -y 1.0
inkscape 0GM.svg -e 0GM.png -w 48 -b '#000000' -y 1.0
inkscape 1RM.svg -e 1RM.png -w 48 -b '#000000' -y 1.0
inkscape 1YM.svg -e 1YM.png -w 48 -b '#000000' -y 1.0
inkscape 1GM.svg -e 1GM.png -w 48 -b '#000000' -y 1.0
inkscape 2RM.svg -e 2RM.png -w 48 -b '#000000' -y 1.0
inkscape 2YM.svg -e 2YM.png -w 48 -b '#000000' -y 1.0
inkscape 2GM.svg -e 2GM.png -w 48 -b '#000000' -y 1.0
inkscape 3RM.svg -e 3RM.png -w 48 -b '#000000' -y 1.0
inkscape 3YM.svg -e 3YM.png -w 48 -b '#000000' -y 1.0
inkscape 3GM.svg -e 3GM.png -w 48 -b '#000000' -y 1.0
inkscape 4RM.svg -e 4RM.png -w 48 -b '#000000' -y 1.0
inkscape 4YM.svg -e 4YM.png -w 48 -b '#000000' -y 1.0
inkscape 4GM.svg -e 4GM.png -w 48 -b '#000000' -y 1.0
inkscape 5RM.svg -e 5RM.png -w 48 -b '#000000' -y 1.0
inkscape 5YM.svg -e 5YM.png -w 48 -b '#000000' -y 1.0
inkscape 5GM.svg -e 5GM.png -w 48 -b '#000000' -y 1.0
inkscape 6RM.svg -e 6RM.png -w 48 -b '#000000' -y 1.0
inkscape 6YM.svg -e 6YM.png -w 48 -b '#000000' -y 1.0
inkscape 6GM.svg -e 6GM.png -w 48 -b '#000000' -y 1.0
inkscape 7RM.svg -e 7RM.png -w 48 -b '#000000' -y 1.0
inkscape 7YM.svg -e 7YM.png -w 48 -b '#000000' -y 1.0
inkscape 7GM.svg -e 7GM.png -w 48 -b '#000000' -y 1.0
inkscape 8RM.svg -e 8RM.png -w 48 -b '#000000' -y 1.0
inkscape 8YM.svg -e 8YM.png -w 48 -b '#000000' -y 1.0
inkscape 8GM.svg -e 8GM.png -w 48 -b '#000000' -y 1.0
inkscape 9RM.svg -e 9RM.png -w 48 -b '#000000' -y 1.0
inkscape 9YM.svg -e 9YM.png -w 48 -b '#000000' -y 1.0
inkscape 9GM.svg -e 9GM.png -w 48 -b '#000000' -y 1.0
inkscape dRM.svg -e dRM.png -w 48 -b '#000000' -y 1.0
inkscape dYM.svg -e dYM.png -w 48 -b '#000000' -y 1.0
inkscape dGM.svg -e dGM.png -w 48 -b '#000000' -y 1.0
convert -crop 24x80+12+0 dRM.png dRM.png
convert -crop 24x80+12+0 dGM.png dGM.png
convert -crop 24x80+12+0 dYM.png dYM.png


##Copy 
/bin/cp -f *.png ../src/pomodoro/images/
