// Données d'embouteillages (collectées manuellement)
export const trafficZones = [
  {
    id: 'route-parcelles',
    name: 'Route des Parcelles',
    severity: 'high',
    timeSlots: [
      { start: '07:00', end: '09:00', level: 'high' },
      { start: '17:00', end: '19:00', level: 'high' }
    ],
    coordinates: { lat: 14.7644, lng: -17.4139 }
  },
  {
    id: 'corniche',
    name: 'Corniche Ouest',
    severity: 'medium',
    timeSlots: [
      { start: '08:00', end: '10:00', level: 'medium' },
      { start: '18:00', end: '20:00', level: 'medium' }
    ],
    coordinates: { lat: 14.7167, lng: -17.4667 }
  }
];

// Fonction pour vérifier si c'est une heure de pointe
export const isRushHour = (timeString) => {
  const hour = parseInt(timeString.split(':')[0]);
  return (hour >= 7 && hour <= 9) || (hour >= 17 && hour <= 19);
};