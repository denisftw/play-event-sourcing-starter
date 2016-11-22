import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';


class AppComponent {
  init = () => {
    this.renderComponent();
  };
  initState = () => {
    this.initLoginRedirecting();
  };
  initLoginRedirecting = () => {
    axios.interceptors.response.use((response) => {
      return response;
    }, (error) => {
      if (error.response.status === 401) {
        window.location = '/login';
      }
      return Promise.reject(error);
    });
  };
  renderComponent = () => {
    const reactDiv = document.getElementById('reactDiv');
    if (!!reactDiv) {
      this.initState();
      ReactDOM.render(<h1>App initialized</h1>, reactDiv);
    }
  }
}

export default AppComponent;
