import org.junit.*;
import static org.junit.Assert.*;

public class Lab1WhiteBox{
  private Set set;
  private Set removeSet;

  @Before
  public void init(){
    set = new Set();
    removeSet = new Set();
  }

  /* ####################################################################
                            EXERCISE A
   #################################################################### */

   // Statement coverage +  Branch coverage

   // Tests insert when number already exist
   @Test
   public void testAlreadyExistInSet(){
     set.insert(1);
     set.insert(1);
     assertTrue(set.toArray().length == 1);
   }

   // Test insert when ascending order
   @Test
   public void testInsertAscending(){
     set.insert(1);
     set.insert(2);
     set.insert(3);

     assertTrue(set.toArray().length == 3);
     assertTrue(set.toArray()[0] == 1);
     assertTrue(set.toArray()[1] == 2);
     assertTrue(set.toArray()[2] == 3);
   }

   // Test insert when ascending order
   @Test
   public void testInsertDescending(){
     set.insert(3);
     set.insert(2);
     set.insert(1);

     assertTrue(set.toArray().length == 3);
     assertTrue(set.toArray()[0] == 1);
     assertTrue(set.toArray()[1] == 2);
     assertTrue(set.toArray()[2] == 3);
   }

   /* ####################################################################
                             EXERCISE B
    #################################################################### */

    // Statement coverage and Branch coverage

    // Test that checks if no members exists
    @Test
    public void testNoMembers(){
      assertFalse(set.member(1));
    }

    // Test that checks if a member exist
    @Test
    public void testExistingMembers(){
      set.insert(1);
      assertTrue(set.member(1));
    }

    // Test that checks if it can get member from a value lower than existing
    @Test
    public void testLowerMemberExist(){
      set.insert(2);
      set.insert(3);
      set.insert(4);
      assertFalse(set.member(1));
    }

    // Test that checks if it can get member from a value higher than existing
    @Test
    public void testHigherMemberExist(){
      set.insert(2);
      set.insert(3);
      set.insert(4);
      assertFalse(set.member(5));
    }

    /* ####################################################################
                              EXERCISE C
     #################################################################### */

     // Statement coverage and Branch coverage

     // test if section works
     @Test
     public void testSection(){
       set.insert(1);
       removeSet.insert(1);
       set.section(removeSet);
       assertTrue(set.toArray().length == 0);
     }

     // test when both are empty
     @Test
     public void testEmpty(){
       set.section(removeSet);
       assertTrue(set.toArray().length == 0);
     }

     // test to see if section can remove unspecified values
     @Test
     public void testWrongRemove(){
       set.insert(1);
       removeSet.insert(2);
       set.section(removeSet);
       assertTrue(set.toArray().length == 1);
     }

     // test to see if section can remove the unspecified values, reverse
     @Test
     public void testWrongRemoveReverse(){
       set.insert(2);
       removeSet.insert(1);
       set.section(removeSet);
       assertTrue(set.toArray().length == 1);
     }

     // test to see if one is bigger than the other
     @Test
     public void testSetLargerThanRemoveSet(){
       set.insert(1);
       set.insert(2);
       set.insert(3);
       removeSet.insert(1);
       removeSet.insert(2);
       set.section(removeSet);
       assertTrue(set.member(3));
       assertTrue(set.toArray().length == 1);
     }

     // test to see if one is bigger than the other
     @Test
     public void testRemoveLargerThanSet(){
       set.insert(1);
       set.insert(2);
       removeSet.insert(1);
       removeSet.insert(3);
       removeSet.insert(4);
       set.section(removeSet);
       assertTrue(set.member(2));
       assertTrue(set.toArray().length == 1);
     }

     /* ####################################################################
                               EXERCISE D
      #################################################################### */

      // Statement coverage and branch coverage

      // test if no arith triple
      @Test
      public void testNoArithTriple(){
        set.insert(100);
		    set.insert(44);
		    set.insert(2);
        assertFalse(set.containsArithTriple());
      }

      // test if arith triple
      @Test
      public void testArithTriple(){
        set.insert(1);
        set.insert(2);
        set.insert(3);
        assertTrue(set.containsArithTriple());
      }

      // test if arith triple when other elements in set.
      @Test
      public void testArithTrippleMessy(){
        set.insert(-1);
        set.insert(-6);
        set.insert(2);
        set.insert(0);
        set.insert(1);
        assertTrue(set.containsArithTriple());
      }
}
