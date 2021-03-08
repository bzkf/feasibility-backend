package de.numcodex.feasibility_gui_backend.service.query_executor;

/**
 * Indicates that a site was not found with regards to a specific query.
 */
public class SiteNotFoundException extends Exception {
    public SiteNotFoundException(String queryId, String siteId) {
        super("Site with ID '" + siteId + "' was not found with regards to query with ID '" + queryId + "'.");
    }
}
