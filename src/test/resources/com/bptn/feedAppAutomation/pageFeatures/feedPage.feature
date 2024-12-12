Feature: Feed Page

 @WebTest
  Scenario: Add a new feed
    Given User is on the dashboard "/app/dashboard"
     When User clicks on the 'Upload Image' button with image "/assets/image.jpg" to add feed
     Then A cropper should appear to add feed
     When User clicks on the 'Crop' button to finalize the image for the feed
     And User enters the feed content "This is my new feed description"
     And User clicks on the 'Send' button to add feed
     Then The feed should be created successfully and User should see the message "Feed has been added."
     When User clicks on "My Feeds" which navigates to my feeds page
     Then User should see the newly created feed content "This is my new feed description" on my feeds page

      @WebTest
  Scenario: Validate profile
    Given User is on the my feeds page "/app/myFeeds"
    When User clicks on "Profile" which navigates to profile page
    Then the form fields should have the following values:
       | fieldName | expectedValue        |
       | firstName | Yuvraj               |
       | lastName  | Singh                |
       | phone     | 6471234567           |
       | emailId   | me@iyuvraajsingh.com |
	  When User clears the following fields:
	    | firstName |
	    | lastName  |
	    | phone     |
	    | emailId   |
	    | password  |
	  And User updates the profile fields with the following values:
	    | fieldName | value               |
	    | firstName | James               |
	    | lastName  | Dar                 |
	    | phone     | 890677              |
	    
	    | password  | Hello@123           |
	  And User submits the profile form
	  Then The confirmation message should be "Basic profile saved." 
		   
   