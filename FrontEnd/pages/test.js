import { Container, Grid } from '@material-ui/core'
import React from 'react'
import HorizontalMenu from '../src/components/general/HorizontalMenu'

export default function test() {
    return (
            <Container maxWidth="lg">
                <Grid container>
                    <Grid item xs={3}>
                        <HorizontalMenu menuItems={
                            [
                                {
                                  title: "My account",
                                  selected: false,
                                  onClick: () => {
                                    alert("123123");
                                  },
                                },
                                {
                                  title: "My account",
                                  selected: false,
                                  onClick: () => {
                                    alert("123123");
                                  },
                                },
                                {
                                  title: "My account",
                                  selected: true,
                                  onClick: () => {
                                    alert("123123");
                                  },
                                },
                                {
                                  title: "My account",
                                  selected: false,
                                  onClick: () => {
                                    alert("123123");
                                  },
                                },
                              ]
                        } />
                    </Grid>
                </Grid>
            </Container>
    )
}
