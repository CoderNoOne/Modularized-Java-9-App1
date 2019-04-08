module modelapp {
  exports model to convertersapp, mainapp, servicesapp;
  opens model to gson;
}