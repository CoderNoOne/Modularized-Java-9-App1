module modelapp {
  exports model to convertersapp, mainapp, servicesapp, validatorapp;
  opens model to gson;
}