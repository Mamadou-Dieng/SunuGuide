import React, { useState } from 'react';
import { Container, Row, Col, Card, Form, Button, Alert, Badge, Offcanvas } from 'react-bootstrap';
import { Search, MapPin, Navigation, AlertTriangle, Menu, Settings, History, MapPinned, X } from 'lucide-react';
import { popularDestinations } from '../data/destinations';
import { trafficZones, isRushHour } from '../data/trafficData';

export default function Home({ onSearch, onNavigateToMenu }) {
  const [departure, setDeparture] = useState('');
  const [destination, setDestination] = useState('');
  const [showMenu, setShowMenu] = useState(false);

  const handleSearch = (e) => {
    e.preventDefault();
    if (departure && destination) {
      onSearch({ departure, destination });
    }
  };

  const handleQuickDestination = (dest) => {
    setDestination(dest.name);
  };

  const handleMenuClick = (page) => {
    setShowMenu(false);
    onNavigateToMenu(page);
  };

  // Vérifier s'il y a des embouteillages actuellement
  const currentTime = new Date().toTimeString().slice(0, 5);
  const activeTraffic = trafficZones.filter(zone => 
    isRushHour(currentTime)
  );

  const menuItems = [
    { id: 'settings', label: 'Paramètres', icon: Settings, color: 'primary' },
    { id: 'history', label: 'Historique', icon: History, color: 'success' },
    { id: 'tracking', label: 'Suivi en cours', icon: MapPinned, color: 'warning' }
  ];

  return (
    <>
      <div className="d-flex flex-column h-100 bg-light">
        {/* Header */}
        <div className="bg-primary text-white p-4" style={{ borderRadius: '0 0 1.5rem 1.5rem' }}>
          <Container>
            <div className="d-flex justify-content-between align-items-center mb-3">
              {/* Menu Hamburger */}
              <Button 
                variant="link" 
                className="text-white p-0"
                onClick={() => setShowMenu(true)}
              >
                <Menu size={28} />
              </Button>

              {/* Logo */}
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

      {/* Side Menu (Offcanvas) */}
      <Offcanvas show={showMenu} onHide={() => setShowMenu(false)} placement="start">
        <Offcanvas.Header className="border-bottom">
          <Offcanvas.Title className="d-flex align-items-center gap-2">
            <Navigation size={24} className="text-primary" />
            <span className="fw-bold">SunuGuide</span>
          </Offcanvas.Title>
          <Button 
            variant="link" 
            className="text-dark p-0"
            onClick={() => setShowMenu(false)}
          >
            <X size={24} />
          </Button>
        </Offcanvas.Header>
        
        <Offcanvas.Body className="p-0">
          {/* User Info */}
          <div className="p-4 bg-light border-bottom">
            <div className="d-flex align-items-center gap-3">
              <div className="bg-primary text-white rounded-circle d-flex align-items-center justify-content-center" 
                   style={{ width: '50px', height: '50px' }}>
                <span className="fw-bold fs-5">V</span>
              </div>
              <div>
                <h6 className="mb-0 fw-bold">Visiteur</h6>
                <p className="mb-0 small text-muted">JOJ Dakar 2026</p>
              </div>
            </div>
          </div>

          {/* Menu Items */}
          <div className="p-2">
            {menuItems.map((item) => (
              <Button
                key={item.id}
                variant="link"
                className="w-100 text-start text-decoration-none text-dark d-flex align-items-center gap-3 p-3 rounded"
                onClick={() => handleMenuClick(item.id)}
              >
                <div className={`bg-${item.color} bg-opacity-10 rounded p-2`}>
                  <item.icon size={20} className={`text-${item.color}`} />
                </div>
                <span className="fw-medium">{item.label}</span>
              </Button>
            ))}
          </div>

          {/* Footer */}
          <div className="position-absolute bottom-0 w-100 p-4 border-top bg-light">
            <p className="text-center small text-muted mb-0">
              Version 1.0.0 • JOJ Dakar 2026
            </p>
          </div>
        </Offcanvas.Body>
      </Offcanvas>
    </>
  );
}