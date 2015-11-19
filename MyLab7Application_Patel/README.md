Look and Feel (10 points): The quality of your UI will be evaluated in this homework.
Task 1 Navigation Drawer (50 points). In this lab, we will use the navigation drawer as the top-lever navigation. You need to have the followings on your drawer:
• A nice-looking banner (header)
• 3 items, each containing an icon and a title (text)
• A separator
• 3 more items, each containing only a title (text)
For the 6 items listed above, when they are clicked, they should lead to the following:
• One item should lead to the loading of Fragment in Task 2
• One item should lead to the loading of movie recyclerview (list style)
• One item should lead to the loading of movie recyclerview (grid style)
• The other three item should lead to the loading of other fragments (of your choice); a simple fragment with an ImageView or TextView is fine.
• A selected item on the drawer should have a different background (marking it as selected), and it should stay that way until another item is selected.
Task 2 (10 points): Write a fragment called “Demo Fragment”. Each button in this fragment should lead to the demonstration of one task (Tasks 3, 4, and 5) in this homework. You can reuse the cover page fragment from the previous homework.
Task 3 (10 points): Create a button called “Dialog Demo” on your demo fragment. When this button is clicked, a dialog shows up. There should be at least 3 input widgets on this dialog (e.g. DatePicker, EditText, etc.). Once the user clicks the “OK” button on the dialog, the inputs should return back to your demo fragment, which displays the returned data in a TextView (or other places of your choices).
Task 4 (10 points): Create a button called “Activity Demo” on your demo fragment. When this button is clicked, another activity (instead of a dialog) shows up. This activity displays a fragment that contains at least 3 input widgets, a “Cancel” button, and an “OK” button. Once the user clicks the “OK” button, the data from the input widgets should return back to your demo fragment and be displayed.
Task 5 (10 points): Create a button called “Contact Demo” on your demo fragment. When this button is clicked, the Contact app will come up; once the user selects an entry in the contact, the selected entry will be sent back to the demo fragment and be displayed.
