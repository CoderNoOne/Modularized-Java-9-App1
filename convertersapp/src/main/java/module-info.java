module convertersapp {
  requires gson;
  requires java.sql;
  requires exceptionsapp;
  requires modelapp;
  exports converters.json to mainapp;
  exports converters.others to servicesapp;
}