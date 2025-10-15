import React, { useState } from 'react';
import Home from './pages/Home';
import SearchResults from './pages/SearchResults';
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

  return (
    <div className="d-flex justify-content-center align-items-center min-vh-100 bg-light p-4">
      {/* Phone Frame */}
      <div className="phone-frame">
        <div className="phone-screen d-flex flex-column">
          {/* Page Content */}
          <div className="flex-grow-1 overflow-hidden">
            {currentPage === 'home' && <Home onSearch={handleSearch} />}
            {currentPage === 'search' && (
              <SearchResults 
                searchData={searchData} 
                onBack={handleBack}
                onSelectRoute={handleSelectRoute}
              />
            )}
          </div>

          {/* Bottom Navigation */}
          <Navbar currentPage={currentPage} onNavigate={handleNavigate} />
        </div>
      </div>
    </div>
  );
}