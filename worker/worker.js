
self.addEventListener("message", function(e) {
  var args = e.data.args;  
  args[2] = args[1] - 100 / 2;
  postMessage( args[2] );
});