import psrqpy
import pandas as pd
import os

def fetch_pulsar_data():
    query = psrqpy.QueryATNF(params=[
        "PSRJ",         # Name
        "P0",           # Period
        "P1",           # Period Derivative
        "AGE",          # Age
        "BSURF",        # Surface Magnetic Field
        "DIST",         # Distance
        "DM",           # Dispersion Measure
        "EDOT"          # Spin-down Luminosity
    ])

    return query.table.to_pandas()

def save_to_csv(df, filename="../data/pulsars_data.csv"):
    os.makedirs(os.path.dirname(filename), exist_ok=True)
    df.to_csv(filename, index=False)

if __name__ == "__main__":
    save_to_csv(fetch_pulsar_data())
