/**
  * @authors: Yasmin Vega-Nuno
  * @date: 3-14-21
  */

package team_3.transactionserver;

import java.util.*;

public class Lock
{

    // List of transactions who hold a lock
    // if there are more than 1, it could only be read lock
    Vector holders;
    // List of transactions who need a lock
    Vector requestors;
    // types of locks a transaction can have
    enum LockType{READ, WRITE, NONE};
    LockType lockType;
    // The account the lock is associated with
    Object account;

    public Lock(Object account, LockType lockType)
    {
        this.account = account;
        this.lockType = lockType;
        this.holders = new Vector();
        this.requestors = new Vector();
    }

    public boolean isHoldersNotEmpty()
    {
        return !(this.holders.isEmpty());
    }

    public boolean isLockTypeNotRead(LockType aLockType)
    {
        return !(aLockType == LockType.READ);
    }

    public boolean isTransNotAloneInHolders(Transaction transaction)
    {
        return  !((this.holders.size() == 1) && (this.holders.contains(transaction)));
    }

    /** Acquires a lock on an object (account in this context). If the lock
     * type is not available, waits until it is. There are three conditions:
     * no locks held, read lock(s) held, or a single write lock held. When
     * acquiring a write lock, the must be no read lock on the object, but a
     * read lock may be acquired if there are other read locks. A write lock
     * can only be obtained if there are no write locks and no read locks on
     * the object other than the lock belonging to who is trying to promote to
     * the write lock.
     * 
     * @param transaction - Transaction object that is trying to access the
     * locked object.
     * @param aLockType - The lock type to acquire.
     */
    public synchronized void acquire(Transaction transaction, LockType aLockType)
    {
        // check for any write locks and wait
        while (this.lockType == LockType.WRITE)
        {
            try
            {
                System.out.println("Waiting on write lock " + ((Account)this.account).accountNum);
                
                // add trans to list of requestors
                this.requestors.addElement(transaction);

                // while in conflict, wait
                wait();

                // when we exit wait, remove trans from list of requestors
                this.requestors.removeElement(transaction);
            }
            catch(InterruptedException e)
            {
                //probably change
                System.out.println("InterruptedException ocurred.");
            }
        }
        // at this point, there can only be no locks or read locks held

        // if trying to acquire a read lock
        if (aLockType == LockType.READ)
        {
            // no write locks, aquire read lock
            this.lockType = LockType.READ;
            this.holders.add(transaction);
        }
        
        // if trying to acquire a write lock
        else if (aLockType == LockType.WRITE)
        {
            // check if transaction hold a read lock
            if (this.holders.contains(transaction)
                    && this.lockType == LockType.READ)
            {
                // wait on more than one read lock held
                while (this.holders.size() > 1
                        && this.holders.contains(transaction)) // maintains lock
                {
                    try
                    {
                        System.out.println("Waiting on read locks " + this.holders);
                        
                        // add trans to list of requestors
                        this.requestors.addElement(transaction);

                        // while in conflict, wait
                        wait();

                        // when we exit wait, remove trans from list of requestors
                        this.requestors.removeElement(transaction);
                    }
                    catch(InterruptedException e)
                    {
                        //probably change
                        System.out.println("InterruptedException ocurred.");
                    }
                }
                
                // promote lock
                promote();
            }
            
            // no read lock, problem!
            else
            {
                System.out.println("Transaction does not hold a read lock!");
            }
        }
    }

    /**
     * 
     */
    public void promote()
    {
      // upgrade lock type on current object
      // read to write
      if(this.lockType == LockType.READ)
      {
          this.lockType = LockType.WRITE;
      }
    }

    /**
     * 
     * @param transaction 
     */
    public synchronized void release(Transaction transaction)
    {
        // remove this holder
        this.holders.removeElement(transaction);
        // set locktype to none
        this.lockType = LockType.NONE;

        notifyAll();
    }
}