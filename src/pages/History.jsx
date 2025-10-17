import React from 'react';
import { Container, Card, Badge, Button } from 'react-bootstrap';
import { ArrowLeft, Clock, MapPin, Navigation, Trash2 } from 'lucide-react';

export default function History({ onBack }) {
  // Données simulées d'historique
  const historyItems = [
    {
      id: 1,
      from: 'Colobane',
      to: 'Diamniadio',
      date: '2025-10-17',
      time: '14:30',
      transport: 'TER + Bus Tata',
      price: 800,
      status: 'completed'
    },
    {
      id: 2,
      from: 'Plateau',
      to: 'Stade Abdoulaye Wade',
      date: '2025-10-16',
      time: '10:15',
      transport: 'Taxi',
      price: 2000,
      status: 'completed'
    },
    {
      id: 3,
      from: 'Parcelles',
      to: 'Almadies',
      date: '2025-10-15',
      time: '16:45',
      transport: 'Bus Tata',
      price: 300,
      status: 'completed'
    }
  ];

  return (
    <div className="d-flex flex-column h-100 bg-light">
      {/* Header */}
      <div className="bg-success text-white p-4">
        <div className="d-flex align-items-center gap-3 mb-3">
          <Button 
            variant="link" 
            className="text-white p-0"
            onClick={onBack}
          >
            <ArrowLeft size={24} />
          </Button>
          <h5 className="mb-0 fw-bold">Historique des trajets</h5>
        </div>
        <p className="mb-0 small opacity-75">
          {historyItems.length} trajet{historyItems.length > 1 ? 's' : ''} effectué{historyItems.length > 1 ? 's' : ''}
        </p>
      </div>

      {/* Content */}
      <Container className="flex-grow-1 py-4 overflow-auto">
        {historyItems.length === 0 ? (
          <Card className="text-center py-5">
            <Card.Body>
              <Clock size={48} className="text-muted mb-3" />
              <h6 className="text-muted">Aucun trajet dans l'historique</h6>
              <p className="text-muted small">Vos trajets apparaîtront ici</p>
            </Card.Body>
          </Card>
        ) : (
          historyItems.map((item) => (
            <Card key={item.id} className="mb-3 border-0 shadow-sm">
              <Card.Body className="p-3">
                <div className="d-flex justify-content-between align-items-start mb-3">
                  <Badge bg="success">Terminé</Badge>
                  <Button variant="link" className="text-danger p-0" size="sm">
                    <Trash2 size={16} />
                  </Button>
                </div>

                {/* Route */}
                <div className="mb-3">
                  <div className="d-flex align-items-start gap-2 mb-2">
                    <MapPin size={16} className="text-primary mt-1" />
                    <div>
                      <p className="mb-0 fw-semibold">{item.from}</p>
                    </div>
                  </div>
                  <div className="ms-2 border-start border-2 border-primary ps-3 py-1">
                    <p className="mb-0 small text-muted">{item.transport}</p>
                  </div>
                  <div className="d-flex align-items-start gap-2">
                    <Navigation size={16} className="text-danger mt-1" />
                    <div>
                      <p className="mb-0 fw-semibold">{item.to}</p>
                    </div>
                  </div>
                </div>

                {/* Details */}
                <div className="d-flex justify-content-between align-items-center pt-2 border-top">
                  <div className="d-flex gap-3">
                    <span className="small text-muted">
                      📅 {new Date(item.date).toLocaleDateString('fr-FR')}
                    </span>
                    <span className="small text-muted">
                      🕐 {item.time}
                    </span>
                  </div>
                  <span className="small fw-semibold text-success">
                    {item.price} FCFA
                  </span>
                </div>

                {/* Action Button */}
                <Button variant="outline-primary" size="sm" className="w-100 mt-3">
                  Refaire ce trajet
                </Button>
              </Card.Body>
            </Card>
          ))
        )}

        {/* Clear History Button */}
        {historyItems.length > 0 && (
          <Button variant="outline-danger" className="w-100 mt-3">
            <Trash2 size={16} className="me-2" />
            Effacer tout l'historique
          </Button>
        )}
      </Container>
    </div>
  );
}