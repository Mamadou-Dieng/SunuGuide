// Calcul d'itinéraires multimodaux
export const calculateDistance = (point1, point2) => {
  // Formule de Haversine simplifiée
  const R = 6371; // Rayon de la Terre en km
  const dLat = (point2.lat - point1.lat) * Math.PI / 180;
  const dLon = (point2.lng - point1.lng) * Math.PI / 180;
  
  const a = Math.sin(dLat/2) * Math.sin(dLat/2) +
    Math.cos(point1.lat * Math.PI / 180) * Math.cos(point2.lat * Math.PI / 180) *
    Math.sin(dLon/2) * Math.sin(dLon/2);
  
  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
  const distance = R * c;
  
  return Math.round(distance * 10) / 10; // Arrondi à 1 décimale
};

export const calculateTime = (distance, speed) => {
  // Temps en minutes
  return Math.round((distance / speed) * 60);
};

export const calculatePrice = (distance, pricePerKm) => {
  return Math.round(distance * pricePerKm);
};