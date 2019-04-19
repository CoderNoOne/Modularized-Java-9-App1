module convertersapp {
  requires gson;
  requires java.sql;
  requires exceptionsapp;
  requires modelapp;
  requires validatorapp;
  exports converters.json to mainapp;
  exports converters.others to servicesapp;
}