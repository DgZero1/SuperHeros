package com.example.superheros


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.superheros.data.DataSource

import com.example.superheros.model.Hero
import com.example.superheros.ui.theme.SuperHerosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperHerosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HeroApp()
                }
            }
        }
    }
}@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperHeroAppBar (modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(4.dp),
                    painter = painterResource(id =R.drawable.ic_launcher_foreground),
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        modifier = modifier
    )
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroApp() {
    Scaffold (
        topBar = {
            SuperHeroAppBar()
        }
    ) { it ->
        LazyColumn(contentPadding = it) {
            items(DataSource.heros) {
                HeroItem(
                    hero = it,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun HeroItem(
    hero: Hero,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val color by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.tertiaryContainer
        else MaterialTheme.colorScheme.primaryContainer, label=""
    )

    Card(modifier = modifier

        .clickable {expanded =!expanded}
    )
    {
        Column(
            modifier = modifier

                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium,


                    )
                )
                .background(color = color)
        ) {

        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {


            Column( modifier = Modifier
                .weight(1f)
                .padding(
                    start =4.dp,
                    top = 8.dp,
                    end = 8.dp,
                    bottom = 8.dp)
            )
            {
                Text(
                    text = stringResource(hero.heroResourceID),
                    style = MaterialTheme.typography.displaySmall,
                    //modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = stringResource(hero.descriptionResourceID),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            HeroItemButton(
                expanded = expanded,
                onClick = { expanded = !expanded }
            )
            Image(
                modifier = Modifier
                    .size(72.dp)
                    .padding(0.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop,
                painter = painterResource(hero.imageResourceId),
                contentDescription = null
            )
        }
        if (expanded) {
            HeroWeakness(
                heroResource = hero.vulnResourceId,
                vulnDetail = hero.vulndetailResourceId,
                modifier = Modifier.padding(
                    start = 12.dp,
                    top = 8.dp,
                    end = 16.dp,
                    bottom = 16.dp

                )
            )

        }
    }
}

@Composable
private fun HeroItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}
@Composable
fun HeroWeakness(
    heroResource: Int,
    vulnDetail: Int,

    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(heroResource),
            style = MaterialTheme.typography.labelLarge,
            
        )
        Text(
            text = stringResource(vulnDetail),
            style = MaterialTheme.typography.bodyMedium
        )
    }

}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SuperHerosTheme(darkTheme = false) {
        HeroApp()
    }
}