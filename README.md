# UrBuddy

Playstore link : https://play.google.com/store/apps/details?id=com.shivendra.hp.urbuddy&hl=en
<<>>

FOR MAKING ANY CHANGES IN CODE OR DEVELOPING AND UPGRADING THE SAME CODE YOU CAN ASK ME FOR ADDING YOU AS CONTRIBUTOR AND FORK A BRANCH FOR YOUR DEVELOPMENT.FOR LEARNING PURPOSES YOU CAN CLONE REPO. 
.JUST SEND A SIMPLE MAIL AT shivendra15@iiitkottayam.ac.in and shivam9935@gmail.com FOR ANY REQUEST, MOST PROBABLY YOU WILL GET A POSITIVE RESPONSE XD.

<<>>
This is an android app developed for the use of IIIT kottayam's Students and Related people. Mess hostel and acandmic things + extra 
functionality ,to apply for leave application online(Still needed to be added) and get instant notifiacations about events

1) Appsuport database in firebase contains 
appversion: 0/1    where 0 means app is inactive,1 means app is active don't remove app version otherwise app will again get active

2) Link of techclub@iiitkottayam.ac.in Ur Buddy latest Version is shared in app so don't delete that folder.
and dont off link sharing.

3) Database for home in firebase
 \home\<timestamp in millisecond when it was created>\Text : for text
                                         \Image : for image
                                         \ Heading : for heading
                                         \ UpdateID : Id of the user who uploaded that card
 
4) Storage for home is in home with image name same as in database home\Image value.

5) Share app same point 2 folders link is given so in that folder we have to keep the latest working app.

6) Gallery database
    Gallery\<timestamp in millisecond when it was created>\Caption    Caption of image  
                                                          \Photo       name of photo as saved in storage (under gallery\)
                                                          \UpdateID    Id of user who have uploaded that photo
7) Polls databse structure 
                  Polls\<timestamp in millisecond when it was created>\Positive\UID  :  "1"
                  Polls\<timestamp in millisecond when it was created>\Negative\UID  :  "1"
                  Polls\<timestamp in millisecond when it was created>\Thought   :      stores the thought
                  Polls\<timestamp in millisecond when it was created>\UID  :  UID of Poster
                  
8) Reward video database
                 Rewards\<UID> : it's value contains reward point earned.

9) Acadmics is hardcodded mostly and needed more work 
Storage structure a database acadmic contains name of the image for each values ex. 
                      annualP1   : ap1.jpg : annual planner first page
                      annualP2   : ap2.jpg : annual planner second page
                      timeT1     : tt1.jpg : time table for 1 st year
                      timeT2     : tt2.jpg : time table for 2 nd year
                                tt3.jpg : time table for 3 rd year
                                 tt4.jpg : time table for 4 th year
                                 
                                 READ ISSUE SECTION BEFORE UPLOADING ANY IMAGE TO STORAGE
                                 
 10) A cron job is setup (using shivam9935@gmail.com) to take the backup of nUser ( part of database which contains booking) at 1:00 P.M. which will serve as the counting file for counter app of your buddy.
 
 11) snacks counter app will be counting from "CountSnapshot" database and it is updated 2 times in a day at 10 A.M. and 1:00 P.M.(Can be changed in future)
 
 12) Mess menu image name is stored in database messmeu/Photoname , so, before updating storage bucket update this database too and aread ISSUE SECTION TO AVOIDE IMAGE CACHING ISSUE.
 
 
 ISSUE : While hardcoding image name in code and fetching it from database make a cache in mobile which then after deleting the original image still loads the image
 https://github.com/firebase/FirebaseUI-Android/issues/479
 
 To counter issue database contains name of the image , everytime when we upload changed image we rename if i.e. tt1.jpg we will change it to tt11.jpg so that image cache would be invalidated
 
 same thing with messmenu also.
 
                           
