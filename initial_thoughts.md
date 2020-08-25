# Mi a fasz lesz velunk

## User 
  balance
  * ArrayList -> userIncomes
  * -"- -> userExpense
  * -"- -> userDebt

## Group
  * balace --> 0 ha minden rendezve van
  * store incomes and expensess
  * list of users

### Household
  * users in this group have more confidence for each other and are able to see the balances of one-another 

_____________

## income 
* 0 -> user
* id, date, currency, type, mennyiség, 

## expense
* user -> 0
* same as above
  

### recurring expenses

## debt
* user --> user

## investment
* not liquidifiable money that is not an expense

## groupExpense
* store which users contributed and generate a bunch of debts between users

_____

## labels
* two layered -> type and note ? -> unique for each instance

## exchanger
* helps to estimate the current balance between currencies

_____

## UserGraph 

* store the known users and groups for one user. able to make and handle requests for other users or gropus or data.

