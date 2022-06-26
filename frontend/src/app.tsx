import * as React from 'react';
import {
    BrowserRouter as Router,
    Switch,
    Route
  } from 'react-router-dom';
import { PageLanding } from './page/page-landing/page.landing';
import Page404 from './page/page-404/page.404';

export default function App() {
    return (
        <>
            <Router>
                <main>
                    {/* A <Switch> looks through its children <Route>s and
                        renders the first one that matches the current URL. */}
                    <Switch>
                        <Route path="/" exact>
                            <PageLanding />
                        </Route>
                        <Route path="*">
                            <Page404 />
                        </Route>
                    </Switch>
                </main>
            </Router>
        </>
        
    );
  }
