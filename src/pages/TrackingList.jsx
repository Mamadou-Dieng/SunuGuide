import React from 'react';
import { Container, Card, Badge, Button, ProgressBar } from 'react-bootstrap';
import { ArrowLeft, MapPin, Navigation, Clock, TrendingUp } from 'lucide-react';

export default function TrackingList({ onBack, onViewTracking }) {
  // Trajets en cours (simulés)
  const activeTrips = [
    {
      id: 1,
      from: 'Colobane',
      to: 'Gare Dakar',
      transport: 'TER',
      progress: 65,
      distanceRemaining: '2.5 km',
      timeRemaining: '8 min',
      status: 'in-progress'
    }
  ];

  return (
    <div className="d-flex flex-column h-100 bg-light">
      {/* Header */}
      <div className="bg-warning text-dark p-4">
        <div className="d-flex align-items-center gap-3 mb-3">
          <Button 
            variant="link" 
            className="text-dark p-0"
            onClick={onBack}
          >
            <ArrowLeft size={24} />
          </Button>
          <h5 className="mb-0 fw-bold">Suivi en cours</h5>
        </div>
        <p className="mb-0 small opacity-75">
          {activeTrips.length} trajet{activeTrips.length > 1 ? 's' : ''} en cours
        </p>
      </div>

      {/* Content */}
      <Container className="flex-grow-1 py-4 overflow-auto">
        {activeTrips.length === 0 ? (
          <Card className="text-center py-5">
            <Card.Body>
              <MapPin size={48} className="text-muted mb-3" />
              <h6 className="text-muted">Aucun trajet en cours</h6>
              <p className="text-muted small">Commencez un trajet pour le voir ici</p>
              <Button variant="primary" className="mt-3" onClick={onBack}>
                Rechercher un itinéraire
              </Button>
            </Card.Body>
          </Card>
        ) : (
          activeTrips.map((trip) => (
            <Card key={trip.id} className="mb-3 border-0 shadow-lg">
              <Card.Body className="p-4">
                <div className="d-flex justify-content-between align-items-start mb-3">
                  <Badge bg="success" className="px-3 py-2">
                    🚆 {trip.transport}
                  </Badge>
                  <Badge bg="warning" text="dark">En cours</Badge>
                </div>

                {/* Route */}
                <div className="mb-4">
                  <div className="d-flex align-items-center gap-2 mb-2">
                    <MapPin size={18} className="text-primary" />
                    <span className="fw-semibold">{trip.from}</span>
                  </div>
                  <div className="ms-3">
                    <ProgressBar 
                      now={trip.progress} 
                      variant="success"
                      style={{ height: '6px' }}
                      className="mb-2"
                    />
                  </div>
                  <div className="d-flex align-items-center gap-2">
                    <Navigation size={18} className="text-danger" />
                    <span className="fw-semibold">{trip.to}</span>
                  </div>
                </div>

                {/* Stats */}
                <div className="bg-light rounded p-3 mb-3">
                  <div className="row g-3">
                    <div className="col-6">
                      <div className="text-center">
                        <h3 className="text-primary fw-bold mb-1">{trip.distanceRemaining}</h3>
                        <p className="small text-muted mb-0">Distance restante</p>
                      </div>
                    </div>
                    <div className="col-6">
                      <div className="text-center">
                        <h3 className="text-success fw-bold mb-1">{trip.timeRemaining}</h3>
                        <p className="small text-muted mb-0">Temps restant</p>
                      </div>
                    </div>
                  </div>
                </div>

                {/* Alert */}
                <div className="bg-info bg-opacity-10 rounded p-3 mb-3">
                  <div className="d-flex align-items-center gap-2">
                    <TrendingUp size={16} className="text-info" />
                    <span className="small text-info fw-medium">
                      Vous arriverez dans environ {trip.timeRemaining}
                    </span>
                  </div>
                </div>

                {/* Action Button */}
                <Button 
                  variant="primary" 
                  className="w-100"
                  onClick={() => onViewTracking(trip)}
                >
                  📍 Voir sur la carte
                </Button>
              </Card.Body>
            </Card>
          ))
        )}
      </Container>
    </div>
  );
}