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

![](/Users/simrankhanna/Desktop/Screenshot 2024-04-21 at 10.50.15 AM.png)

2. After the transaction happens successfully:
![Screenshot 2024-04-21 at 10.53.17 AM.png](..%2F..%2F..%2F..%2Fvar%2Ffolders%2Fpv%2Fp5zx_6bx71l3dv3924n5rktm0000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_ZatZWR%2FScreenshot%202024-04-21%20at%2010.53.17%E2%80%AFAM.png)
![](/Users/simrankhanna/Desktop/Screenshot 2024-04-21 at 10.37.45 AM.png)

Now, when an error occur in between the transaction - Let's say account 4 does not exist anymore:
1. Atomic transaction - Balance of account 3 remains same when the transaction fails due to account 4 not being present in DB.
![Screenshot 2024-04-21 at 11.04.36 AM.png](..%2F..%2F..%2F..%2Fvar%2Ffolders%2Fpv%2Fp5zx_6bx71l3dv3924n5rktm0000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_KKDx0d%2FScreenshot%202024-04-21%20at%2011.04.36%E2%80%AFAM.png)
![](/Users/simrankhanna/Desktop/Screenshot 2024-04-21 at 10.38.29 AM.png)
2. Non-Atomic Transaction - setting auto commit true for each statement.
![Screenshot 2024-04-21 at 11.06.14 AM.png](..%2F..%2F..%2F..%2Fvar%2Ffolders%2Fpv%2Fp5zx_6bx71l3dv3924n5rktm0000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_9U3oc7%2FScreenshot%202024-04-21%20at%2011.06.14%E2%80%AFAM.png)
![Screenshot 2024-04-21 at 11.52.31 AM.png](..%2F..%2F..%2F..%2Fvar%2Ffolders%2Fpv%2Fp5zx_6bx71l3dv3924n5rktm0000gn%2FT%2FTemporaryItems%2FNSIRD_screencaptureui_fwrBxB%2FScreenshot%202024-04-21%20at%2011.52.31%E2%80%AFAM.png)
