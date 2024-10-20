You have to create a LESCO billing system using java and file I/O. To implement the system, you have to
implement following requirements


1. The LESCO billing system shall maintain records of usernames and passwords of LESCO
employees. This information can be kept in a file with name EmployeesData. The
Employeesdata file shall have one row for each employee, in each row there are two comma
separated values, the first value is username and the second value is password. Usernames
shall be unique (i.e. no two rows will have the same username appearing in them).
Employees can change their passwords by providing their username and current password.

2. The LESCO billing system shall maintain record of customer information in a file named
CustomerInfo. The CustomersInfo file shall save the information such as a unique 4 digit
customer id (generated by the system), customer's CNIC number (13 digit number provided
by the customer without dashes), Customer name, Address, Phone number, Customer Type
(commercial or domestic), meter type (Single Phase or Three Phase), Connection Date,
Regular Units Consumed, Peak Hour Units Consumed. All these fields will be comma
separated. Customer id and CNIC number may serve as username and password for the
LESCO customers respectively. The Regular Units Consumed field corresponds to the meter
reading for which the customer has paid the bill. In other words this field represents the
units consumed (for both types of meters) and paid for at a given time. It is set to zero when
a new meter is installed and a row is added in the CustomersInfo file. Then this field is
updated whenever a bill is paid (its status is set to paid by the LESCO employees). The Peak
Hours Units Consumed is also similar to the Regular Units Consumed but it is set for 3-phase
meters only. For the 1-phase meters, it is kept blank. These two fields will be used to
calculate the number of units consumed in a particular month.


3. The LESCO billing system shall maintain record of billing information in a file named
BillingInfo. The BillingInfo file shall store information such as 4 digit customer id (borrowed
from the CustomersInfo file), Billing month, Current Meter Reading Regular, Current Meter
Reading Peak, Reading Entry Date in format DD/MM/YYYY (cannot be in future, consider it
to be issue date of the bill), Cost of electricity, Sales Tax Amount, Fixed Charges, Total Billing
amount, Due Date in format DD/MM/YYYY (7 calendar days after the entry of Current Meter
Reading), Bill Paid Status (Paid or Unpaid), Bill payment Date in format DD/MM/YYYY
(cannot be before Reading Entry Date). There will be a new row for each meter customer
every month in this file. For single phase meters, the unit price of electricity is 5 rupees per
unit for domestic customers and 15 rupees per unit for the commercial customers. For three
phase meters two meter readings are stored and for the regular units the price is 8 rupees
per unit and for peak hours the price is 12 rupees per unit for domestic customers. For
commercial customers the unit price is 18 rupees and 25 rupees per unit in regular and peak
hours respectively. For the 1-phase meters and the off-peak units in 3-phase meters the
number of units consumed can be calculated by subtracting the Regular Units Consumed
(mentioned in file CustomerInfo) from the Current Meter Reading Regular. For calculation
about the peak hours units subtract the Peak Hours Units from Current Meter Reading Peak.

4. The LESCO billing system shall maintain the information about the sales tax, fixed charges
and the per unit tariff TariffTaxInfo file. This file shall have 4 rows only. The first row keeps
tariff and tax information about domestic customers with 1-phase meter. The second row
keeps tariff and tax information about the commercial customers with 1-phase meter. The
third row keeps tariff and tax information about domestic customers with 3-phase meter.
The fourth row keeps tariff and tax information about the commercial customers with 3-
phase meter. Sample entries are as follows:
1Phase,5,,17,150
1Phase,15,,20,250
3Phase,8,12,17,150
3Phase,18,25,20,250
The entries in a row correspond to the meter type, regular unit price, peak hour unit price
(left blank for 1-phase meters in the first 2 rows), the percentage of tax (for example 17
percent of the cost of electricity), fixed charges.


5. The LESCO billing system shall allow the LESCO employees with valid username and
password (after login) to add or update an entry (or row) in the files CustomerInfo and
BillingInfo. The employees will add a row in CustomerInfo file whenever a new meter is
installed for a customer. The system shall not allow more than 3 meters against a CNIC. If
an employee tries to enter more than 3 meters for a CNIC, the shall disallow the action and
display an error message stating "Not Allowed! Maximum 3 meters allowed per CNIC.". The
system has access to NADRADB file which stores three comma separated values for each
CNIC: CNIC Number (13 digit without dashes), Issuance Date (format DD/MM/YYYY), Expiry
Date (format DD/MM/YYYY). The NADRADB file shall have multiple records (one for each
person with a CNIC)

6. The employees will add a row in BillingInfo every month when the meter reading needs to
be entered in the system. The employees will update the Bill Paid Status field in the
BillingInfo file once they receive offline information about payment against a bill. Once this
information is changed, the system shall automatically update the Total Units Consumed
field in the file CustomersInfo file to Current Meter Reading.


7. The LESCO billing system shall allow the LESCO employees with valid username and
password (after login) to add or update entries in the TariffTaxInfo file. The system shall
make sure that exactly 4 rows exist in this file. The employees can update the tariff, tax etc.
whenever they find it appropriate.

8. The LESCO billing system shall allow the LESCO employees with valid username and
password (after login) to view any bill after providing its 4 digit customer id.

9. The LESCO billing system shall allow the LESCO customers to view their current bill. The
customers need to provide their 4-digit customer id, their CNIC number, their meter type
(1-phase or 3-phase), and their current meter reading(s). The bill should separately show
the cost of electricity, tax, and the fixed charges. The same bill should also show the total
amount due and the due date of the bill. In addition, the bill payment status should also be
shown on the bill (as per the records in the relevant LESCO files). The bill should display the
customer id, customer name, address, phone number, customer type, meter type, and tariff
also. This bill needs not be saved in any file. It is used for view purpose only.
10. The LESCO billing system shall allow the LESCO employees with valid username and
password (after login) to view reports of paid and unpaid bills. The report shall only show
the amount of unpaid bills so far and the amount of paid bills so far.

11. The LESCO billing system shall allow the LESCO employees with valid username and
password (after login) to view reports of customers whose CNIC is about to expire in next
30 days. The system shall lookup the NADRADB file to know the expiry dates of CNICs.
12. The LESCO billing system shall allow the LESCO customers to update the expiry date of their
own CNIC mentioned in NADRADB file. The system shall require the customers to enter their
CNIC number and customer number before they can update the expiry date.



![7816980a-5c42-47f5-ac2e-cde20e552772](https://github.com/user-attachments/assets/ec0216a4-effa-4e52-b73c-bbd27150bfe1)

