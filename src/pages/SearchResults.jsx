import React, { useState } from 'react';
import { Container, Card, Button, Badge, Collapse } from 'react-bootstrap';
import { ArrowLeft, Clock, DollarSign, ChevronRight, Train, Bus, Car, ArrowRight, Footprints } from 'lucide-react';
import { transports } from '../data/transports';
import { calculateDistance, calculateTime, calculatePrice } from '../utils/calculateRoute';
import { formatDuration } from '../utils/formatTime';
import { formatPrice } from '../utils/formatPrice';

export default function SearchResults({ searchData, onBack, onSelectRoute }) {
  const [expandedRoute, setExpandedRoute] = useState(null);

  // Routes simulées (à remplacer par ta vraie logique)
  const routes = [
    {
      id: 1,
      type: 'direct',
      transport: transports.find(t => t.id === 'taxi'),
      duration: 45,
      price: 3000,
      badge: 'Direct',
      badgeVariant: 'warning'
    },
    {
      id: 2,
      type: 'multi',
      name: 'TER + Bus Tata',
      duration: 50,
      price: 800,
      badge: 'Économique',
      badgeVariant: 'success',
      segments: [
        {
          transport: 'TER',
          icon: Train,
          from: 'Colobane',
          to: 'Gare Dakar',
          duration: 15,
          price: 500,
          color: 'success'
        },
        {
          transport: 'Marche',
          icon: Footprints,
          duration: 5,
          isWalk: true
        },
        {
          transport: 'Bus Tata',
          icon: Bus,
          from: 'Gare Dakar',
          to: 'Diamniadio',
          duration: 30,
          price: 300,
          color: 'primary'
        }
      ]
    },
    {
      id: 3,
      type: 'multi',
      name: 'Bus + Car Rapide',
      duration: 58,
      price: 450,
      badge: 'Moins cher',
      badgeVariant: 'info',
      segments: [
        {
          transport: 'Bus Tata',
          icon: Bus,
          from: 'Colobane',
          to: 'Parcelles',
          duration: 20,
          price: 250,
          color: 'primary'
        },
        {
          transport: 'Marche',
          icon: Footprints,
          duration: 3,
          isWalk: true
        },
        {
          transport: 'Car Rapide',
          icon: Bus,
          from: 'Parcelles',
          to: 'Diamniadio',
          duration: 35,
          price: 200,
          color: 'danger'
        }
      ]
    }
  ];

  const getTransportIcon = (transportId) => {
    const iconMap = {
      'taxi': Car,
      'ter': Train,
      'bus-tata': Bus,
      'car-rapide': Bus
    };
    return iconMap[transportId] || Bus;
  };

  return (
    <div className="d-flex flex-column h-100 bg-light">
      {/* Header */}
      <div className="bg-white border-bottom p-3">
        <div className="d-flex align-items-center mb-3">
          <Button 
            variant="link" 
            className="text-dark p-0 me-3"
            onClick={onBack}
          >
            <ArrowLeft size={24} />
          </Button>
          <div className="flex-grow-1">
            <p className="text-muted small mb-0">De: {searchData.departure}</p>
            <p className="fw-semibold mb-0">Vers: {searchData.destination}</p>
          </div>
        </div>

        {/* Filters */}
        <div className="d-flex gap-2 overflow-auto pb-2">
          <Button variant="primary" size="sm" className="rounded-pill">Tous</Button>
          <Button variant="outline-secondary" size="sm" className="rounded-pill">Direct</Button>
          <Button variant="outline-secondary" size="sm" className="rounded-pill">Moins cher</Button>
          <Button variant="outline-secondary" size="sm" className="rounded-pill">Plus rapide</Button>
        </div>
      </div>

      {/* Routes List */}
      <Container className="flex-grow-1 py-3 overflow-auto">
        {routes.map((route) => {
          const Icon = route.type === 'direct' ? getTransportIcon(route.transport.id) : Train;
          
          return (
            <Card 
              key={route.id} 
              className="mb-3 border-2 transport-card"
              style={{ borderColor: expandedRoute === route.id ? '#0d6efd' : '#dee2e6' }}
            >
              <Card.Body 
                className="p-3"
                onClick={() => setExpandedRoute(expandedRoute === route.id ? null : route.id)}
                style={{ cursor: 'pointer' }}
              >
                <div className="d-flex align-items-start">
                  <div 
                    className={`bg-${route.badgeVariant} bg-opacity-10 rounded-3 p-3 me-3`}
                    style={{ width: '56px', height: '56px' }}
                  >
                    <Icon size={32} className={`text-${route.badgeVariant}`} />
                  </div>

                  <div className="flex-grow-1">
                    <div className="d-flex justify-content-between align-items-start mb-2">
                      <h6 className="mb-0 fw-bold">
                        {route.type === 'direct' ? route.transport.name : route.name}
                      </h6>
                      <Badge bg={route.badgeVariant}>{route.badge}</Badge>
                    </div>

                    <p className="text-muted small mb-2">
                      {route.type === 'direct' ? 'Trajet sans correspondance' : `${route.segments.filter(s => !s.isWalk).length} correspondances`}
                    </p>

                    <div className="d-flex justify-content-between align-items-center">
                      <div className="d-flex gap-3">
                        <div className="d-flex align-items-center text-muted small">
                          <Clock size={14} className="me-1" />
                          <span>{route.duration} min</span>
                        </div>
                        <div className="d-flex align-items-center text-muted small">
                          <DollarSign size={14} className="me-1" />
                          <span>{formatPrice(route.price)}</span>
                        </div>
                      </div>
                      <ChevronRight 
                        size={20} 
                        className={`text-secondary transition ${expandedRoute === route.id ? 'rotate-90' : ''}`}
                      />
                    </div>
                  </div>
                </div>
              </Card.Body>

              {/* Expanded Details */}
              <Collapse in={expandedRoute === route.id}>
                <div>
                  {route.type === 'multi' && (
                    <Card.Body className="border-top bg-light p-3">
                      <p className="small fw-semibold text-secondary mb-3">📍 Détails du trajet :</p>

                      {route.segments.map((segment, idx) => {
                        const SegmentIcon = segment.icon;
                        
                        return (
                          <div key={idx}>
                            {segment.isWalk ? (
                              <div className="d-flex align-items-center gap-2 py-2 bg-white rounded px-3 mb-2">
                                <div className="bg-secondary bg-opacity-10 rounded-circle p-2">
                                  <SegmentIcon size={16} className="text-secondary" />
                                </div>
                                <span className="small text-secondary">
                                  🚶 Marche à pied • {segment.duration} min
                                </span>
                              </div>
                            ) : (
                              <div className="d-flex align-items-start gap-2 mb-2">
                                <div className={`bg-${segment.color} rounded-circle p-2`} style={{ width: '32px', height: '32px' }}>
                                  <SegmentIcon size={16} className="text-white" />
                                </div>
                                <div className="flex-grow-1 bg-white rounded p-2">
                                  <div className="d-flex align-items-center gap-2 mb-1">
                                    <span className="small fw-semibold">{segment.transport}</span>
                                    <span className="small text-muted">• {segment.duration} min</span>
                                  </div>
                                  <div className="d-flex align-items-center gap-1 small text-muted mb-1">
                                    <span>{segment.from}</span>
                                    <ArrowRight size={14} />
                                    <span>{segment.to}</span>
                                  </div>
                                  <span className={`small text-${segment.color} fw-medium`}>
                                    {formatPrice(segment.price)}
                                  </span>
                                </div>
                              </div>
                            )}

                            {idx < route.segments.length - 1 && (
                              <div className="ms-3 border-start border-2 border-secondary" style={{ height: '16px', width: '2px' }}></div>
                            )}
                          </div>
                        );
                      })}

                      <Button 
                        variant="primary" 
                        className="w-100 mt-3"
                        onClick={() => onSelectRoute(route)}
                      >
                        ✅ Choisir cet itinéraire
                      </Button>
                    </Card.Body>
                  )}

                  {route.type === 'direct' && (
                    <Card.Body className="border-top bg-light p-3">
                      <p className="text-muted small mb-3">
                        ✅ Trajet direct sans correspondance
                      </p>
                      <Button 
                        variant="primary" 
                        className="w-100"
                        onClick={() => onSelectRoute(route)}
                      >
                        ✅ Choisir cet itinéraire
                      </Button>
                    </Card.Body>
                  )}
                </div>
              </Collapse>
            </Card>
          );
        })}
      </Container>
    </div>
  );
}