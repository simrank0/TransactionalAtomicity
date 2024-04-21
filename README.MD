# Atomicity

Refer to this article written by me:
[Ensuring Consistent Transactions](https://open.substack.com/pub/simrankhanna/p/ensuring-consistent-transactions?r=1ga7b5&utm_campaign=post&utm_medium=web)
I've tried to showcase how atomicity in transactions actually take place. 


### Example

We're taking an example of 2 accounts (id = 3 and 4). 
A transaction takes place where money is supposed to get deducted from account id = 3 and should be added to account id = 4. 
If this wasn't atomic, this transaction might fail when money is deducted from account id = 3 before adding the same to other account. 


Let's see a happy scenario. 

1. Initially these are the account balances. 

![](https://github.com/simrank0/TransactionalAtomicity/assets/56548348/b688b7a4-47d8-4f0c-b21f-e3405c75a1a0)

2. After the transaction happens successfully:

![](https://github.com/simrank0/TransactionalAtomicity/assets/56548348/5c6db2b8-9f85-489c-b860-ca8444f9b784)
![](https://github.com/simrank0/TransactionalAtomicity/assets/56548348/e25811cf-f0f7-4fd1-94f3-51517d147601)

Now, when an error occur in between the transaction - Let's say account 4 does not exist anymore:
1. Atomic transaction - Balance of account 3 remains same when the transaction fails due to account 4 not being present in DB.
![](https://github.com/simrank0/TransactionalAtomicity/assets/56548348/982bf0ba-917e-4cd9-afcb-a3717e43e403)
![](https://github.com/simrank0/TransactionalAtomicity/assets/56548348/3d0b6854-b91e-4fe2-841b-f997c9d6f187)
2. Non-Atomic Transaction - setting auto commit true for each statement.
![](https://github.com/simrank0/TransactionalAtomicity/assets/56548348/2061a4c9-3390-4da1-9b11-9f88a3181668)
