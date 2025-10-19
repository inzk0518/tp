---
layout: page
title: User Guide
---

AddressBook Level 3 (AB3) is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `addcontact n/NAME`, `NAME` is a parameter which can be used as `addcontact n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…` after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

_~~Shows a message explaining how to access the help page.~~_

Format: `help`

### Listing all persons : `list`

_~~Shows a list of all persons in the address book.~~_

Format: `list`


### Adding a contact

Adds a new contact to the system, with details of the contact.

Format: `addcontact n/NAME p/PHONE_NUMBER [e/EMAIL] [a/ADDRESS] [min/AMOUNT] [max/AMOUNT] [t/TAG] [notes/TEXT] [s/STATUS]`

<div markdown="span" class="alert alert-primary">:information_source:
TEXT for notes has a maximum length of 500 characters <br>
</div>

Examples:
* `addcontact n/Charlie p/91236789 a/982 Tampines Road t/buyer s/active`
* `addcontact n/Xi Mi p/65738475 e/ximi@example.com min/800000 max/1000000`

### Editing a contact

Edits an existing contact in the address book.

Format: `editcontact UUID [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [min/AMOUNT] [max/AMOUNT] [t/TAG] [notes/TEXT] [s/STATUS]`

* Edits the person that has the UUID specified `UUID`. The UUID refers to the ID number shown in the displayed person list.
* At least one of the optional fields must be provided.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` 
*  `edit 2 n/Betsy Crower t/`

### Filtering contact

Filters the contacts based on the fields given

Format: `filtercontact [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [min/AMOUNT] [max/AMOUNT] [t/TAG] [notes/TEXT] [s/STATUS] [limit/LIMIT] [offset/OFFSET]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* Substring words will be matched e.g. `Han` will match `Hans`
* Persons matching at least one keyword will be returned

Examples:
* `filtercontact a/yishun`
* `filtercontact n/Tan s/active`

### Deleting a contact

Deletes the specified person from the address book.

Format: `delete c/UUID`

* Deletes the person that has the UUID specified `UUID`. The UUID refers to the ID number shown in the displayed person list.

Examples:
* `delete c/1`

### Clearing all entries : `clear`

_~~Clears all entries from the address book.~~_

Format: `clear`

### Exiting the program : `exit`

_~~Exits the program.~~_

Format: `exit`

### Saving the data

~~_AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually._~~

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
