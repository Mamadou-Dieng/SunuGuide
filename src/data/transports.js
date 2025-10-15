// Liste des moyens de transport disponibles
export const transports = [
  {
    id: 'ter',
    name: 'TER',
    type: 'train',
    icon: 'train',
    color: 'green',
    pricePerKm: 100,
    speed: 60, // km/h
    available: true
  },
  {
    id: 'bus-tata',
    name: 'Bus Tata',
    type: 'bus',
    icon: 'bus',
    color: 'blue',
    pricePerKm: 50,
    speed: 30,
    available: true
  },
  {
    id: 'taxi',
    name: 'Taxi',
    type: 'car',
    icon: 'car',
    color: 'orange',
    pricePerKm: 300,
    speed: 40,
    available: true
  },
  {
    id: 'car-rapide',
    name: 'Car Rapide',
    type: 'bus',
    icon: 'bus',
    color: 'red',
    pricePerKm: 40,
    speed: 25,
    available: true
  }
];

// Points de correspondance
export const transferPoints = [
  {
    id: 'gare-dakar',
    name: 'Gare Dakar',
    coordinates: { lat: 14.6937, lng: -17.4441 },
    availableTransports: ['ter', 'bus-tata', 'taxi']
  },
  {
    id: 'parcelles',
    name: 'Parcelles Assainies',
    coordinates: { lat: 14.7644, lng: -17.4139 },
    availableTransports: ['bus-tata', 'car-rapide', 'taxi']
  }
];