import React, { useState } from 'react';
import { Container, Card, Form, Button, ListGroup } from 'react-bootstrap';
import { ArrowLeft, Bell, Globe, Moon, MapPin, Volume2 } from 'lucide-react';

export default function Settings({ onBack }) {
  const [notifications, setNotifications] = useState(true);
  const [darkMode, setDarkMode] = useState(false);
  const [language, setLanguage] = useState('fr');
  const [soundEnabled, setSoundEnabled] = useState(true);

  return (
    <div className="d-flex flex-column h-100 bg-light">
      {/* Header */}
      <div className="bg-primary text-white p-4">
        <div className="d-flex align-items-center gap-3">
          <Button 
            variant="link" 
            className="text-white p-0"
            onClick={onBack}
          >
            <ArrowLeft size={24} />
          </Button>
          <h5 className="mb-0 fw-bold">Paramètres</h5>
        </div>
      </div>

      {/* Content */}
      <Container className="flex-grow-1 py-4 overflow-auto">
        {/* Notifications */}
        <Card className="mb-3 border-0 shadow-sm">
          <Card.Body>
            <div className="d-flex align-items-center justify-content-between">
              <div className="d-flex align-items-center gap-3">
                <div className="bg-primary bg-opacity-10 rounded p-2">
                  <Bell size={20} className="text-primary" />
                </div>
                <div>
                  <h6 className="mb-0 fw-semibold">Notifications</h6>
                  <p className="mb-0 small text-muted">Alertes d'arrivée et embouteillages</p>
                </div>
              </div>
              <Form.Check 
                type="switch"
                checked={notifications}
                onChange={(e) => setNotifications(e.target.checked)}
              />
            </div>
          </Card.Body>
        </Card>

        {/* Language */}
        <Card className="mb-3 border-0 shadow-sm">
          <Card.Body>
            <div className="d-flex align-items-center gap-3 mb-3">
              <div className="bg-success bg-opacity-10 rounded p-2">
                <Globe size={20} className="text-success" />
              </div>
              <div>
                <h6 className="mb-0 fw-semibold">Langue</h6>
                <p className="mb-0 small text-muted">Choisir la langue de l'application</p>
              </div>
            </div>
            <Form.Select value={language} onChange={(e) => setLanguage(e.target.value)}>
              <option value="fr">🇫🇷 Français</option>
              <option value="en">🇬🇧 English</option>
              <option value="wo">🇸🇳 Wolof</option>
            </Form.Select>
          </Card.Body>
        </Card>

        {/* Dark Mode */}
        <Card className="mb-3 border-0 shadow-sm">
          <Card.Body>
            <div className="d-flex align-items-center justify-content-between">
              <div className="d-flex align-items-center gap-3">
                <div className="bg-dark bg-opacity-10 rounded p-2">
                  <Moon size={20} className="text-dark" />
                </div>
                <div>
                  <h6 className="mb-0 fw-semibold">Mode sombre</h6>
                  <p className="mb-0 small text-muted">Activer le thème sombre</p>
                </div>
              </div>
              <Form.Check 
                type="switch"
                checked={darkMode}
                onChange={(e) => setDarkMode(e.target.checked)}
              />
            </div>
          </Card.Body>
        </Card>

        {/* Sound */}
        <Card className="mb-3 border-0 shadow-sm">
          <Card.Body>
            <div className="d-flex align-items-center justify-content-between">
              <div className="d-flex align-items-center gap-3">
                <div className="bg-warning bg-opacity-10 rounded p-2">
                  <Volume2 size={20} className="text-warning" />
                </div>
                <div>
                  <h6 className="mb-0 fw-semibold">Sons</h6>
                  <p className="mb-0 small text-muted">Notifications sonores</p>
                </div>
              </div>
              <Form.Check 
                type="switch"
                checked={soundEnabled}
                onChange={(e) => setSoundEnabled(e.target.checked)}
              />
            </div>
          </Card.Body>
        </Card>

        {/* About */}
        <Card className="mb-3 border-0 shadow-sm">
          <Card.Header className="bg-white fw-semibold">À propos</Card.Header>
          <ListGroup variant="flush">
            <ListGroup.Item className="d-flex justify-content-between">
              <span>Version</span>
              <span className="text-muted">1.0.0</span>
            </ListGroup.Item>
            <ListGroup.Item className="d-flex justify-content-between">
              <span>Développé pour</span>
              <span className="text-muted">JOJ Dakar 2026</span>
            </ListGroup.Item>
          </ListGroup>
        </Card>

        {/* Buttons */}
        <div className="d-grid gap-2">
          <Button variant="outline-danger">Effacer le cache</Button>
          <Button variant="outline-primary">Contacter le support</Button>
        </div>
      </Container>
    </div>
  );
}