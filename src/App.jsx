import React, { useState } from 'react';
import Home from './pages/Home';
import SearchResults from './pages/SearchResults';
import Settings from './pages/Settings';
import History from './pages/History';
import TrackingList from './pages/TrackingList';
import Navbar from './components/common/Navbar';

export default function App() {
  const [currentPage, setCurrentPage] = useState('home');
  const [searchData, setSearchData] = useState(null);

  const handleSearch = (data) => {
    setSearchData(data);
    setCurrentPage('search');
  };

  const handleBack = () => {
    setCurrentPage('home');
  };

  const handleSelectRoute = (route) => {
    console.log('Route sélectionnée:', route);
    // TODO: Navigate to map view
  };

  const handleNavigate = (page) => {
    setCurrentPage(page);
  };

  const handleNavigateToMenu = (page) => {
    setCurrentPage(page);
  };

  const handleViewTracking = (trip) => {
    console.log('Voir trajet:', trip);
    // TODO: Navigate to tracking map
  };

  return (
    <div className="d-flex justify-content-center align-items-center min-vh-100 bg-light p-4">
      {/* Phone Frame */}
      <div className="phone-frame">
        <div className="phone-screen d-flex flex-column">
          {/* Page Content */}
          <div className="flex-grow-1 overflow-hidden">
            {currentPage === 'home' && (
              <Home 
                onSearch={handleSearch}
                onNavigateToMenu={handleNavigateToMenu}
              />
            )}
            {currentPage === 'search' && (
              <SearchResults 
                searchData={searchData} 
                onBack={handleBack}
                onSelectRoute={handleSelectRoute}
              />
            )}
            {currentPage === 'settings' && (
              <Settings onBack={handleBack} />
            )}
            {currentPage === 'history' && (
              <History onBack={handleBack} />
            )}
            {currentPage === 'tracking' && (
              <TrackingList 
                onBack={handleBack}
                onViewTracking={handleViewTracking}
              />
            )}
          </div>

          {/* Bottom Navigation - Only show on certain pages */}
          {['home', 'search', 'map', 'chat', 'profile'].includes(currentPage) && (
            <Navbar currentPage={currentPage} onNavigate={handleNavigate} />
          )}
        </div>
      </div>
    </div>
  );
}