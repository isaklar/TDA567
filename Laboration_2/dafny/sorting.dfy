class sorting{

  /*
   * Predicate sorted
   * @param seq<int>: sequence of integers
   */
  predicate sorted(a : seq<int>)
  {
    forall i :: 0 <= i < |a| ==> forall j :: i <= j < |a| ==> a[i] <= a[j]
    // dis might work as well
    // forall i, k :: 0 <= i < k < |a| ==> a[i] <= a[k]
  }

  /*
   * Predicate sorted'
   * @param seq<int>: sequence of integers
   */
  predicate sorted'(a : seq<int>, min:int, max:int)
  requires 0 <= min <= max <= |a|;
  {
    forall i :: min <= i < max ==> forall j :: i <= j < |a| ==> a[i] <= a[j]
    // dis might work as well
    // forall i, k :: min <= i < k < max ==> a[i] <= a[k]
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
  lemma sorted1AndSorted2(a : seq<int>, min:int, max:int)
  requires 0 <= min <= max <= |a|
  requires sorted(a)
  ensures sorted'(a, min, max)
  {}

  /*
   * Lemma "if a sequence is sorted, then it is sorted' "
   * @param seq<int>: sequence of integers
   * @param min:int: min value in the sequence
   * @param max:int: min value in the sequence
   */
  lemma sorted2AndSorted1(a : seq<int>, min:int, max:int)
  requires 0 <= min <= max <= |a|
  requires sorted'(a, min, max)
  ensures sorted(a[min .. max])
  {}

  // Exercise 4a
  method sortArray(a : array<int>)
  modifies a;
  requires a != null && sorted'(a[..], 0, a.Length);
  requires a.Length > 0
  ensures sorted'(a[..], 0, a.Length);
  ensures p(a[..], old(a[..]))
  {
  }
}
