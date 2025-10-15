import React, { useState } from 'react';
import { Container, Row, Col, Card, Form, Button, Alert, Badge } from 'react-bootstrap';
import { Search, MapPin, Navigation, AlertTriangle } from 'lucide-react';
import { popularDestinations } from '../data/destinations';
import { trafficZones, isRushHour } from '../data/trafficData';

export default function Home({ onSearch }) {
  const [departure, setDeparture] = useState('');
  const [destination, setDestination] = useState('');

  const handleSearch = (e) => {
    e.preventDefault();
    if (departure && destination) {
      onSearch({ departure, destination });
    }
  };

  const handleQuickDestination = (dest) => {
    setDestination(dest.name);
  };

  // Vérifier s'il y a des embouteillages actuellement
  const currentTime = new Date().toTimeString().slice(0, 5);
  const activeTraffic = trafficZones.filter(zone => 
    isRushHour(currentTime)
  );

  return (
    <div className="d-flex flex-column h-100 bg-light">
      {/* Header */}
      <div className="bg-primary text-white p-4" style={{ borderRadius: '0 0 1.5rem 1.5rem' }}>
        <Container>
          <div className="d-flex justify-content-between align-items-center mb-3">
            <div className="d-flex align-items-center gap-2">
              <Navigation size={24} />
              <h5 className="mb-0 fw-bold">SunuGuide</h5>
            </div>
            <Badge bg="light" text="dark">JOJ 2026</Badge>
          </div>
          <div className="text-center">
            <h2 className="fw-bold mb-2">Bienvenue à Dakar</h2>
            <p className="mb-0 opacity-75">Trouvez votre chemin facilement</p>
          </div>
        </Container>
      </div>

      {/* Main Content */}
      <Container className="flex-grow-1 py-4" style={{ marginTop: '-2rem' }}>
        {/* Search Card */}
        <Card className="shadow-lg mb-4">
          <Card.Body className="p-4">
            <h5 className="fw-bold mb-4">Où allez-vous ?</h5>
            
            <Form onSubmit={handleSearch}>
              {/* Departure Input */}
              <Form.Group className="mb-3">
                <div className="position-relative">
                  <div className="position-absolute top-50 translate-middle-y ms-3">
                    <div className="bg-primary bg-opacity-10 rounded-circle p-2">
                      <MapPin size={20} className="text-primary" />
                    </div>
                  </div>
                  <Form.Control
                    type="text"
                    placeholder="Point de départ"
                    value={departure}
                    onChange={(e) => setDeparture(e.target.value)}
                    className="ps-5 py-3 border-0 bg-light"
                    style={{ paddingLeft: '4rem' }}
                  />
                </div>
              </Form.Group>

              {/* Destination Input */}
              <Form.Group className="mb-3">
                <div className="position-relative">
                  <div className="position-absolute top-50 translate-middle-y ms-3">
                    <div className="bg-danger bg-opacity-10 rounded-circle p-2">
                      <Navigation size={20} className="text-danger" />
                    </div>
                  </div>
                  <Form.Control
                    type="text"
                    placeholder="Destination"
                    value={destination}
                    onChange={(e) => setDestination(e.target.value)}
                    className="ps-5 py-3 border-0 bg-light"
                    style={{ paddingLeft: '4rem' }}
                  />
                </div>
              </Form.Group>

              {/* Search Button */}
              <Button 
                variant="primary" 
                type="submit" 
                className="w-100 py-3 fw-semibold"
                size="lg"
              >
                <Search size={20} className="me-2" />
                Rechercher un itinéraire
              </Button>
            </Form>
          </Card.Body>
        </Card>

        {/* Traffic Alert */}
        {activeTraffic.length > 0 && (
          <Alert variant="warning" className="d-flex align-items-start mb-4">
            <AlertTriangle size={20} className="me-3 flex-shrink-0 mt-1" />
            <div>
              <Alert.Heading as="h6" className="mb-1">Embouteillage signalé</Alert.Heading>
              <p className="mb-0 small">
                {activeTraffic[0].name} - Forte affluence de 7h à 9h
              </p>
            </div>
          </Alert>
        )}

        {/* Quick Destinations */}
        <div className="mb-4">
          <h6 className="text-secondary fw-semibold mb-3">Destinations populaires</h6>
          <Row xs={2} className="g-3">
            {popularDestinations.map((dest) => (
              <Col key={dest.id}>
                <Card 
                  className="h-100 border-0 bg-light transport-card"
                  onClick={() => handleQuickDestination(dest)}
                >
                  <Card.Body className="d-flex align-items-center p-3">
                    <span className="fs-3 me-2">{dest.icon}</span>
                    <span className="small fw-medium text-truncate">{dest.name}</span>
                  </Card.Body>
                </Card>
              </Col>
            ))}
          </Row>
        </div>
      </Container>
    </div>
  );
}