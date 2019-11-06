const CONFIG = {
  //location.hostname
  NAME: "Izicash",
  URL_API: location.protocol+'//'+ location.hostname + ":8090/api/",
  IMAGE_PATH: location.protocol+'//'+ location.hostname + ":8090/app/",
};
CONFIG.install = function (Vue, options) {
  Vue.prototype.$getConst = (key) => {
    return CONFIG[key];
  }
};
export default CONFIG;
