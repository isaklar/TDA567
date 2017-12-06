class Token{
  var fingerprint : int;
  var clearanceLevel : int;
  var valid : bool;

  method init(fingerprint : int, clearanceLevel : int)
  modifies this;
  requires clearanceLevel == 3 || clearanceLevel == 2 || clearanceLevel == 1;
  ensures this.fingerprint == fingerprint && this.clearanceLevel == clearanceLevel && valid == true;
  {
    this.fingerprint := fingerprint;
		this.clearanceLevel := clearanceLevel;
		valid := true;
  }

}

class IdStation{
  var reqClearance : int;
  var alarm : bool;

  method init(reqClearance : int)
  modifies this;
	requires reqClearance == 3 || reqClearance == 2 || reqClearance == 1;
	ensures this.reqClearance == reqClearance && !alarm;
	{
		this.reqClearance := reqClearance;
		alarm := false;
	}

  method EnterDoor(user : User, fingerprint : int) returns (access : bool)
	modifies user.token`valid, `alarm;
	requires user != null && user.token != null;
	ensures !old(user.token.valid) || !fingerprintValid(user.token, fingerprint) ==> !access && !user.token.valid && alarm;
	ensures old(user.token.valid) && fingerprintValid(user.token, fingerprint) && clearanceLevelValid(user.token) ==> access && user.token.valid && !alarm;
	ensures old(user.token.valid) && fingerprintValid(user.token, fingerprint) && !clearanceLevelValid(user.token) ==> !access && user.token.valid && !alarm;
  {
    if(user.token.valid){
      var isFingerprintValid := fingerprintValid(user.token, fingerprint);
      if(!isFingerprintValid){
        user.token.valid := false;
        alarm := true;
        access := false;
        return;
      }
      access := clearanceLevelValid(user.token);
      alarm := false;
    }else{
      access := false;
      alarm := true;
    }
  }

  function method clearanceLevelValid(token : Token) : bool
  reads token, this;
  requires token != null;
  {
    token.clearanceLevel >= reqClearance
  }

  function method fingerprintValid(token : Token, scannedFingerprint : int) : bool
  reads token;
  requires token != null;
  {
    token.fingerprint == scannedFingerprint
  }

}

class User{
  var token : Token;

  method init()
  modifies this;
  ensures this.token == null;
  {
    this.token :=null;
  }

}


/*
 *  The fresh-keyword. It is sometimes important for the verifier to know that some
 *  given object has been freshly allocated in a given method. For instance, suppose
 *  you have some class with an array field a, which the Init method should
 *  initialise by creating a new array object. Here, one should include the post-condition
 *  fresh(a), to capture this requirement.
 */
class EnrollmentStation{
  var users : set<User>;

  method init()
  modifies this;
	ensures users == {};
	ensures fresh(users);
	{
		users := {};
	}

  method enroll(user : User, fingerprint : int, clearanceLevel : int)
  modifies this, user`token, user.token;
  requires user != null && user.token == null;
  requires clearanceLevel == 3 || clearanceLevel == 2 || clearanceLevel == 1;
  requires user !in users;
  ensures users == old(users) + {user};
  ensures user.token != null;
  ensures user.token.fingerprint == fingerprint;
  ensures user.token.clearanceLevel == clearanceLevel;
  ensures user.token.valid == true;
  ensures fresh(user.token);
  {
    user.token := new Token.init(fingerprint, clearanceLevel);
    users := users + {user};
  }

  method main()
  {
    /* Init EnrollmentStation */
    var enrollmentStation := new EnrollmentStation.init();

    /* Init Doors */
    var clearanceLow := new IdStation.init(1);
    var clearanceMedium := new IdStation.init(2);
    var clearanceHigh := new IdStation.init(3);

    /* Init users */
    var highUserOne := new User.init();
    var highUserTwo := new User.init();

    var lowUserOne := new User.init();
    var lowUserTwo := new User.init();

    var mediumUserOne := new User.init();
    var mediumUserTwo := new User.init();

    /* Enroll test */
    enrollmentStation.enroll(highUserOne, 1, 3);

    assert enrollmentStation.users == {highUserOne};
    assert highUserOne.token.clearanceLevel == 3;
    assert highUserOne.token.fingerprint == 1;
    assert highUserOne.token.valid;

    enrollmentStation.enroll(highUserTwo, 2, 3);

    assert enrollmentStation.users == {highUserOne, highUserTwo};
    assert highUserTwo.token.clearanceLevel == 3;
    assert highUserTwo.token.fingerprint == 2;
    assert highUserTwo.token.valid;

    enrollmentStation.enroll(mediumUserOne, 3, 2);

    assert enrollmentStation.users == {highUserOne, highUserTwo, mediumUserOne};
    assert mediumUserOne.token.clearanceLevel == 2;
    assert mediumUserOne.token.fingerprint == 3;
    assert mediumUserOne.token.valid;

    enrollmentStation.enroll(mediumUserTwo, 4, 2);

    assert enrollmentStation.users == {highUserOne, highUserTwo, mediumUserOne, mediumUserTwo};
    assert mediumUserTwo.token.clearanceLevel == 2;
    assert mediumUserTwo.token.fingerprint == 4;
    assert mediumUserTwo.token.valid;

    enrollmentStation.enroll(lowUserOne, 5, 1);

    assert enrollmentStation.users == {highUserOne, highUserTwo, mediumUserOne, mediumUserTwo, lowUserOne};
    assert lowUserOne.token.clearanceLevel == 1;
    assert lowUserOne.token.fingerprint == 5;
    assert lowUserOne.token.valid;

    enrollmentStation.enroll(lowUserTwo, 6, 1);

    assert enrollmentStation.users == {highUserOne, highUserTwo, mediumUserOne, mediumUserTwo, lowUserOne, lowUserTwo};
    assert lowUserTwo.token.clearanceLevel == 1;
    assert lowUserTwo.token.fingerprint == 6;
    assert lowUserTwo.token.valid;

    /* EnterDoor test */

    /* Fingerprint test: VALID */

    /* High */
    var access := clearanceHigh.EnterDoor(highUserOne, 1);
    assert highUserOne.token.valid;
    assert access;
    assert !clearanceHigh.alarm;

    access := clearanceHigh.EnterDoor(mediumUserOne, 3);
    assert mediumUserOne.token.valid;
    assert !access;
    assert !clearanceHigh.alarm;

    access := clearanceHigh.EnterDoor(lowUserOne, 5);
    assert lowUserOne.token.valid;
    assert !access;
    assert !clearanceHigh.alarm;

    /* Medium */
    access := clearanceMedium.EnterDoor(highUserOne, 1);
    assert highUserOne.token.valid;
    assert access;
    assert !clearanceMedium.alarm;

    access := clearanceMedium.EnterDoor(mediumUserOne, 3);
    assert mediumUserOne.token.valid;
    assert access;
    assert !clearanceMedium.alarm;

    access := clearanceMedium.EnterDoor(lowUserOne, 5);
    assert lowUserOne.token.valid;
    assert !access;
    assert !clearanceMedium.alarm;

    /* Low */
    access := clearanceLow.EnterDoor(highUserOne, 1);
    assert highUserOne.token.valid;
    assert access;
    assert !clearanceLow.alarm;

    access := clearanceLow.EnterDoor(mediumUserOne, 3);
    assert mediumUserOne.token.valid;
    assert access;
    assert !clearanceLow.alarm;

    access := clearanceLow.EnterDoor(lowUserOne, 5);
    assert lowUserOne.token.valid;
    assert access;
    assert !clearanceLow.alarm;

    /* Fingerprint test: INVALID */
    /* High */
    access := clearanceHigh.EnterDoor(highUserTwo, 1);
    assert !highUserTwo.token.valid;
    assert !access;
    assert clearanceHigh.alarm;

    access := clearanceHigh.EnterDoor(mediumUserTwo, 3);
    assert !mediumUserTwo.token.valid;
    assert !access;
    assert clearanceHigh.alarm;

    access := clearanceHigh.EnterDoor(lowUserTwo, 5);
    assert !lowUserTwo.token.valid;
    assert !access;
    assert clearanceHigh.alarm;

    /* Medium */
    access := clearanceMedium.EnterDoor(highUserTwo, 1);
    assert !highUserTwo.token.valid;
    assert !access;
    assert clearanceMedium.alarm;

    access := clearanceMedium.EnterDoor(mediumUserTwo, 3);
    assert !mediumUserTwo.token.valid;
    assert !access;
    assert clearanceMedium.alarm;

    access := clearanceMedium.EnterDoor(lowUserTwo, 5);
    assert !lowUserTwo.token.valid;
    assert !access;
    assert clearanceMedium.alarm;

    /* Low */
    access := clearanceLow.EnterDoor(highUserTwo, 1);
    assert !highUserTwo.token.valid;
    assert !access;
    assert clearanceLow.alarm;

    access := clearanceLow.EnterDoor(mediumUserTwo, 3);
    assert !mediumUserTwo.token.valid;
    assert !access;
    assert clearanceLow.alarm;

    access := clearanceLow.EnterDoor(lowUserTwo, 5);
    assert !lowUserTwo.token.valid;
    assert !access;
    assert clearanceLow.alarm;
  }
}
