import psrqpy
import pandas as pd
import os

def fetch_pulsar_data():
    query = psrqpy.QueryATNF(params=[
        "PSRJ",     # J2000 Name
        "BNAME",    # Besselian Name
        "RAJ",      # Right Ascension (hms)
        "DECJ",     # Declination (dms)
        "RAJD",     # Right Ascension (deg)
        "DECJD",    # Declination (deg)
        "PMRA",     # Proper motion in RA
        "PMDEC",    # Proper motion in DEC
        "PML",      # Proper motion in Galactic Longitude
        "PMB",      # Proper motion in Galactic Latitude
        "PX",       # Parallax
        "POSEPOCH", # Epoch of position
        "GL",       # Galactic Longitude
        "GB",       # Galactic Latitude
        "ELONG",    # Ecliptic Longitude
        "ELAT",     # Ecliptic Latitude
        "PMELONG",  # Proper motion in ELONG
        "PMELAT",   # Proper motion in ELAT
        "P0",       # Period
        "P1",       # Period Derivative
        "AGE",      # Characteristic Age
        "BSURF",    # Surface Magnetic Field
        "DM",       # Dispersion Measure
        "DIST",     # Distance
        "EDOT"      # Spin-down Luminosity
    ])
    
    return query.table.to_pandas()

def save_to_csv(df, filename="../data/pulsars_data.csv"):
    os.makedirs(os.path.dirname(filename), exist_ok=True)
    df.to_csv(filename, index=False)

if __name__ == "__main__":
    save_to_csv(fetch_pulsar_data())
