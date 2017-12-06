class sorting{

  predicate sorted(nums : seq<int>)
  {
  	forall i : int :: 0 <= i < |nums| - 1 ==> nums[i] <= nums[i + 1]
  }

  predicate sorted'(nums : seq<int>)
  {
  	!(exists i : int :: 0 <= i < |nums| - 1 && nums[i] > nums[i + 1])
  }

  predicate p(a : seq<int>, b : seq<int>)
  {
    multiset(a) == multiset(b)
  }

  predicate p'(a : seq<int>, b : seq<int>)
  requires |a| == |b|;
  {
    //forall a, b :: count(a, b)
    forall i :: count(a,i) == count(b,i)
  }

  function count(a: seq<int>, i : int): nat
  {
    if |a| == 0 then 0 else
   (if a[0] == i then 1 else 0) + count(a[1..], i)
  }


  /*
   * Lemma "if a sequence is sorted, then it is sorted' "
   * @param seq<int>: sequence of integers
   * @param min:int: min value in the sequence
   * @param max:int: min value in the sequence
   */
  lemma sorted1AndSorted2(a : seq<int>)
  //requires 0 <= min <= max <= |a|
  requires sorted(a)
  ensures sorted'(a)
  {}

  /*
   * Lemma "if a sequence is sorted', then it is sorted "
   * @param seq<int>: sequence of integers
   * @param min:int: min value in the sequence
   * @param max:int: min value in the sequence
   */
  lemma sorted2AndSorted1(a : seq<int>)
  //requires 0 <= min <= max <= |a|
  requires sorted'(a)
  ensures sorted(a)
  {}

  // Exercise 4a
  method sortArray(a : array<int>)
  modifies a;
  requires a != null && sorted'(a[..]);
  requires a.Length > 0
  ensures sorted'(a[..]);
  ensures p(a[..], old(a[..]))
  {
  }
}
